package com.wei.iotplatformservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wei.iotplatformservice.pojo.DeviceDataNumber;

import java.util.Map;

public interface DeviceDataNumberService extends IService<DeviceDataNumber> {

    Map<String, Object> queryDeviceDataNumberListP(Integer start, Integer limit, Long cid, String startTime, String endTime);

    Map<String, Object> queryDeviceDataNumberValue(Long cid, String startTime, String endTime);
}
