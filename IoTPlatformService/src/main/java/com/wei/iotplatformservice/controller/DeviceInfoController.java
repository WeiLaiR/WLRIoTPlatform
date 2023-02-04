package com.wei.iotplatformservice.controller;

import com.wei.iotplatformservice.pojo.DeviceInfo;
import com.wei.iotplatformservice.service.DeviceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/platform/deviceInfo")
public class DeviceInfoController {

    private DeviceInfoService deviceInfoService;

    @Autowired
    public void setDeviceInfoService(DeviceInfoService deviceInfoService) {
        this.deviceInfoService = deviceInfoService;
    }

    /**
     * 新增硬件信息接口
     * @param deviceInfo 硬件信息
     * @return 状态值
     */
    @PostMapping("/add")
    public Map<String, Object> newDeviceInfo(@RequestBody DeviceInfo deviceInfo) {
        return deviceInfoService.newDeviceInfo(deviceInfo);
    }


}
