package com.wei.iotplatformservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wei.iotplatformservice.pojo.DeviceInfo;

import java.util.Map;

public interface DeviceInfoService extends IService<DeviceInfo> {

    Map<String, Object> newDeviceInfo(DeviceInfo deviceInfo);
}
