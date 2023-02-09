package com.wei.iotplatformservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wei.iotplatformservice.mapper.DeviceInfoMapper;
import com.wei.iotplatformservice.pojo.DeviceInfo;
import com.wei.iotplatformservice.service.DeviceInfoService;
import com.wei.iotplatformservice.utils.DeviceTokenUtil;
import com.wei.iotplatformservice.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
            map.put("message", "设备新增成功");
            map.put("deviceToken", deviceToken);
        } else {
            map.put("status", 400);
            map.put("message", "设备新增失败");
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

        map.put("status", 200);
        map.put("message", "查询成功");
        map.put("data", infos);
        map.put("total", count);

        return map;
    }
}
