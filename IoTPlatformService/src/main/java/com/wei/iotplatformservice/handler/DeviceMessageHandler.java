package com.wei.iotplatformservice.handler;

import com.wei.iotplatformservice.mapper.DeviceCfgMapper;
import com.wei.iotplatformservice.mapper.DeviceDataMapper;
import com.wei.iotplatformservice.mapper.DeviceDataNumberMapper;
import com.wei.iotplatformservice.mapper.DeviceInfoMapper;
import com.wei.iotplatformservice.pojo.DeviceCfg;
import com.wei.iotplatformservice.pojo.DeviceData;
import com.wei.iotplatformservice.pojo.DeviceDataNumber;
import com.wei.iotplatformservice.pojo.DeviceInfo;
import com.wei.iotplatformservice.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Component
public class DeviceMessageHandler {

    private static final Long TIMEOUT =  30 * 10L;

    private DeviceInfoMapper deviceInfoMapper;
    @Autowired
    public void setDeviceInfoMapper(DeviceInfoMapper deviceInfoMapper) {
        this.deviceInfoMapper = deviceInfoMapper;
    }

    private DeviceCfgMapper deviceCfgMapper;
    @Autowired
    public void setDeviceCfgMapper(DeviceCfgMapper deviceCfgMapper) {
        this.deviceCfgMapper = deviceCfgMapper;
    }

    private DeviceDataNumberMapper deviceDataNumberMapper;
    @Autowired
    public void setDeviceDataNumberMapper(DeviceDataNumberMapper deviceDataNumberMapper) {
        this.deviceDataNumberMapper = deviceDataNumberMapper;
    }

    private DeviceDataMapper deviceDataMapper;
    @Autowired
    public void setDeviceDataMapper(DeviceDataMapper deviceDataMapper) {
        this.deviceDataMapper = deviceDataMapper;
    }

    private ThreadPoolTaskExecutor customThreadPool1;
    @Autowired
    public void setCustomThreadPool1(ThreadPoolTaskExecutor customThreadPool1) {
        this.customThreadPool1 = customThreadPool1;
    }

    private RedisUtil redisUtil;

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Transactional
    public void handleMessage(Map<String, Object> map, String protocol) {
        String token = (String) map.get("token");

        Long did = deviceInfoMapper.queryDeviceIdByToken(token);

        if (did == null) {
            return;
        }

        customThreadPool1.execute(() -> infoTask(map, did, protocol));

        customThreadPool1.execute(() -> dataTask(map, did, protocol));

        redisUtil.set(did + "status", "Y" , TIMEOUT);

    }

    private void infoTask(Map<String, Object> map,Long did ,String protocol) {
        DeviceInfo deviceInfo = deviceInfoMapper.selectById(did);
        if (deviceInfo == null) {
            return;
        }
        boolean sing = false;
        if (deviceInfo.getProtocol() == null) {
            deviceInfo.setProtocol(protocol);
            sing = true;
        }else {
            if (!deviceInfo.getProtocol().equals(protocol)) {
                deviceInfo.setProtocol(protocol);
                sing = true;
            }
        }

        String temp = (String) map.getOrDefault("version", null);

        if (temp != null) {
            double version = Double.parseDouble(temp);

            if (deviceInfo.getVersion() != version) {
                deviceInfo.setVersion(version);
                sing = true;
            }
        }



        if (sing) {
            deviceInfoMapper.updateById(deviceInfo);
        }
    }

    private void dataTask(Map<String, Object> map,Long did ,String protocol) {
        List<DeviceCfg> deviceCfg = deviceCfgMapper.queryDeviceCfgListP(0, 100, did);
        String equipmentNo = (String) map.getOrDefault("equipment_no", null);
        if (equipmentNo == null) {
            equipmentNo = "NO.1";
        }

        for (DeviceCfg cfg : deviceCfg) {
            String s = (String) map.getOrDefault(cfg.getTypeName(), null);
            if (s != null) {
                if (cfg.getIsNumber()) {
                    double val = Double.parseDouble(s);
                    DeviceDataNumber data = new DeviceDataNumber();

                    data.setEquipmentNo(equipmentNo);
                    data.setData(val);
                    data.setDeviceCfgId(cfg.getDeviceCfgId());
                    data.setProtocol(protocol);
                    deviceDataNumberMapper.insert(data);

                }else {
                    DeviceData data = new DeviceData();

                    data.setEquipmentNo(equipmentNo);
                    data.setData(s);
                    data.setDeviceCfgId(cfg.getDeviceCfgId());
                    data.setProtocol(protocol);
                    deviceDataMapper.insert(data);
                }
            }
        }

    }
}
