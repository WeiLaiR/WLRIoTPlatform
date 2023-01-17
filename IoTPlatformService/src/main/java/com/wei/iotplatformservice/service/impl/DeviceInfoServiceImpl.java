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
import java.util.Map;

@Service
public class DeviceInfoServiceImpl extends ServiceImpl<DeviceInfoMapper, DeviceInfo> implements DeviceInfoService {

    private DeviceInfoMapper deviceInfoMapper;

    @Autowired
    public void setDeviceInfoMapper(DeviceInfoMapper deviceInfoMapper) {
        this.deviceInfoMapper = deviceInfoMapper;
    }

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
}
