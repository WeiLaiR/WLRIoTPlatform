package com.wei.iotplatformservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wei.iotplatformservice.pojo.DeviceData;

import java.util.Map;

public interface DeviceDataService extends IService<DeviceData> {

    Map<String, Object> queryDeviceDataListP(Integer start, Integer limit, Long cid, String startTime, String endTime);
}
