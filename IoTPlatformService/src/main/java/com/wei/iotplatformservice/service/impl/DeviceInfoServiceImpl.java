package com.wei.iotplatformservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wei.iotplatformservice.exception.CustomException;
import com.wei.iotplatformservice.handler.DeviceMessageHandler;
import com.wei.iotplatformservice.mapper.DeviceCfgMapper;
import com.wei.iotplatformservice.mapper.DeviceDataMapper;
import com.wei.iotplatformservice.mapper.DeviceDataNumberMapper;
import com.wei.iotplatformservice.mapper.DeviceInfoMapper;
import com.wei.iotplatformservice.pojo.DeviceCfg;
import com.wei.iotplatformservice.pojo.DeviceData;
import com.wei.iotplatformservice.pojo.DeviceDataNumber;
import com.wei.iotplatformservice.pojo.DeviceInfo;
import com.wei.iotplatformservice.service.DeviceInfoService;
import com.wei.iotplatformservice.utils.DeviceTokenUtil;
import com.wei.iotplatformservice.utils.RedisUtil;
import com.wei.iotplatformservice.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeviceInfoServiceImpl extends ServiceImpl<DeviceInfoMapper, DeviceInfo> implements DeviceInfoService {

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

    DeviceDataMapper deviceDataMapper;
    @Autowired
    public void setDeviceDataMapper(DeviceDataMapper deviceDataMapper) {
        this.deviceDataMapper = deviceDataMapper;
    }
    DeviceDataNumberMapper deviceDataNumberMapper;
    @Autowired
    public void setDeviceDataNumberMapper(DeviceDataNumberMapper deviceDataNumberMapper) {
        this.deviceDataNumberMapper = deviceDataNumberMapper;
    }
    private RedisUtil redisUtil;

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }


    /**
     * 新增设备信息
     * @param deviceInfo 设备信息
     * @return 状态值
     */
    @Override
    public Map<String, Object> newDeviceInfo(DeviceInfo deviceInfo) {
        HashMap<String, Object> map = new HashMap<>();

        String deviceToken = DeviceTokenUtil.BuildDeviceToken();

        deviceInfo.setDeviceToken(deviceToken);

        deviceInfo.setUid(TokenUtils.getId());

        if (deviceInfoMapper.insert(deviceInfo) > 0) {
            map.put("status", 200);
            map.put("message", "设备新增成功！");
            map.put("deviceToken", deviceToken);
        } else {
            map.put("status", 400);
            map.put("message", "设备新增失败！");
        }

        return map;
    }

    @Override
    public Map<String, Object> qDeviceInfoListP(String val, Integer start, Integer limit) {
        Map<String, Object> map = new HashMap<>();
        if (val.equals("null")) {
            val = "%";
        }else {
            val = val.concat("%");
        }

        List<DeviceInfo> infos = deviceInfoMapper.qInfoListPQbc(val, start, limit, TokenUtils.getId());
        Integer count = deviceInfoMapper.qInfoListCountQbc();

        ArrayList<Map<String, Object>> maps = new ArrayList<>();

        for (DeviceInfo info : infos) {
            String s = (String) redisUtil.get(info.getDeviceId() + "status");

            HashMap<String, Object> map1 = new HashMap<>();
            map1.put("deviceId", info.getDeviceId());
            map1.put("deviceName", info.getDeviceName());
            map1.put("createTime", info.getCreateTime());
            map1.put("description", info.getDescription());
            map1.put("version", info.getVersion());
            map1.put("protocol", info.getProtocol());
            if (s == null) {
                map1.put("status", "离线");
            }else {
                map1.put("status", "在线");
            }
            maps.add(map1);
        }

        map.put("status", 200);
        map.put("message", "查询成功");
        map.put("data", maps);
        map.put("total", count);

        return map;
    }

    @Override
    public Map<String, Object> queryBriefInfoList() {
        Map<String, Object> map = new HashMap<>();

        List<DeviceInfo> infos = deviceInfoMapper.queryBriefInfoList(TokenUtils.getId());
        ArrayList<Map<String, Object>> maps = new ArrayList<>();

        for (DeviceInfo info : infos) {
            HashMap<String, Object> map1 = new HashMap<>();
            map1.put("text", info.getDeviceName() + " - " + info.getDeviceId());
            map1.put("value", info.getDeviceId());
            maps.add(map1);
        }

        map.put("status", 200);
        map.put("message", "查询成功");
        map.put("data", maps);

        return map;
    }

    @Override
    public Map<String, Object> updateDeviceInfo(DeviceInfo deviceInfo) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            if (deviceInfoMapper.updateById(deviceInfo) > 0) {
                map.put("status", 200);
                map.put("message", "设备信息更新成功!");
            } else {
                map.put("status", 400);
                map.put("message", "设备信息更新失败!");
            }
        }catch (Exception e) {
            throw new CustomException(400, "出现了未知异常！(DeviceInfoUpdate)");
        }
        return map;
    }

    @Override
    public Map<String, Object> deleteDeviceInfo(Long deviceId) {
        Map<String, Object> map = new HashMap<>();

        boolean sign = false;

        try {
            List<DeviceCfg> deviceCfgS = deviceCfgMapper.queryDeviceCfgListP(0, Integer.MAX_VALUE, deviceId);
            for (DeviceCfg deviceCfg : deviceCfgS) {
                if (deviceCfg.getIsNumber()) {
                    if (deviceDataNumberMapper.delete(new QueryWrapper<DeviceDataNumber>().eq("device_cfg_id", deviceCfg.getDeviceCfgId())) < 0) {
                        sign = true;
                    }
                }else {
                    if (deviceDataMapper.delete(new QueryWrapper<DeviceData>().eq("device_cfg_id", deviceCfg.getDeviceCfgId())) < 0) {
                        sign = true;
                    }
                }

                if (deviceCfgMapper.deleteById(deviceCfg.getDeviceCfgId()) < 0) {
                    sign = true;
                }
            }

            if (deviceInfoMapper.deleteById(deviceId) < 0) {
                sign = true;
            }
        } catch (Exception e) {
            throw new CustomException(400, "出现了未知异常！(DeviceInfoDelete)");
        }

        if (sign) {
            throw new CustomException(400, "出现了未知异常！(DeviceInfoDelete)");
        }

        map.put("status", 200);
        map.put("message", "设备删除成功!");

        return map;
    }

    @Override
    public Map<String, Object> createNewToken(Long did) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            String deviceToken = DeviceTokenUtil.BuildDeviceToken();
            DeviceInfo deviceInfo = new DeviceInfo();
            deviceInfo.setDeviceId(did);
            deviceInfo.setDeviceToken(deviceToken);
            if (deviceInfoMapper.updateById(deviceInfo) > 0) {
                map.put("status", 200);
                map.put("message", "设备Token更新成功!");
                map.put("deviceToken", deviceToken);
            } else {
                map.put("status", 400);
                map.put("message", "设备Token更新失败!");
            }
        }catch (Exception e) {
            throw new CustomException(400, "出现了未知异常！(DeviceInfoCreateNewToken)");
        }

        return map;
    }

    @Override
    public Map<String, Object> queryHomeInfo() {
        HashMap<String, Object> map = new HashMap<>();
        try {
            map.put("status", 200);
            map.put("message", "查询成功");
            map.put("deviceNum", deviceInfoMapper.countByDeviceId());
            map.put("actNum", deviceInfoMapper.countByProtocolNotNull());
            map.put("onlineNum", DeviceMessageHandler.getNum());
        }catch (Exception e) {
            throw new CustomException(400, "出现了未知异常！(DeviceInfoQueryHomeInfo)");
        }

        return map;
    }

    @Override
    public void deleteRedisDeviceCfg(Long did) {
        redisUtil.del(did + "cfg");
    }
}
