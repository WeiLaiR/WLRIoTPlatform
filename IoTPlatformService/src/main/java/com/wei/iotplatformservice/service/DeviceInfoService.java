package com.wei.iotplatformservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wei.iotplatformservice.pojo.DeviceInfo;

import java.util.Map;

public interface DeviceInfoService extends IService<DeviceInfo> {

    /**
     * 新增设备信息
     * @param deviceInfo 设备信息
     * @return 状态值
     */
    Map<String, Object> newDeviceInfo(DeviceInfo deviceInfo);

    Map<String, Object> qDeviceInfoListP(String val, Integer start, Integer limit);

    Map<String, Object> queryBriefInfoList();

    Map<String, Object> updateDeviceInfo(DeviceInfo deviceInfo);

    Map<String, Object> deleteDeviceInfo(DeviceInfo deviceInfo);

    Map<String, Object> createNewToken(Long did);

}
