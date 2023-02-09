package com.wei.iotplatformservice.controller;

import com.wei.iotplatformservice.exception.CustomException;
import com.wei.iotplatformservice.pojo.DeviceInfo;
import com.wei.iotplatformservice.service.DeviceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/listP/{start}/{limit}/{val}")
    public Map<String, Object> getDeviceInfoListP(@PathVariable Integer start, @PathVariable Integer limit, @PathVariable String val) {
        if (start >= 0 && limit > 0) {
            return deviceInfoService.qDeviceInfoListP(val, (start - 1) * limit, limit);
        } else {
            throw new CustomException(400, "暂不可查询所有内容！");
        }
    }


}
