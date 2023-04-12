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


    @GetMapping("/briefList")
    public Map<String, Object> getBriefInfoList() {
        return deviceInfoService.queryBriefInfoList();
    }

    @PostMapping("/update")
    public Map<String, Object> updateDeviceInfo(@RequestBody DeviceInfo deviceInfo) {
        return deviceInfoService.updateDeviceInfo(deviceInfo);
    }

    @PostMapping("/createNewToken")
    public Map<String, Object> createNewToken(@RequestBody Long did) {
        return deviceInfoService.createNewToken(did);
    }

    @DeleteMapping("/delete/{deviceId}")
    public Map<String, Object> deleteDeviceInfo(@PathVariable Long deviceId) {
        return deviceInfoService.deleteDeviceInfo(deviceId);
    }

    @DeleteMapping("/deleteRedisDeviceCfg/{did}")
    public void deleteRedisDeviceCfg(@PathVariable Long did) {
        deviceInfoService.deleteRedisDeviceCfg(did);
    }

    @GetMapping("/homeInfo")
    public Map<String, Object> queryHomeInfo() {
        return deviceInfoService.queryHomeInfo();
    }

    @PostMapping("/sendMqttMessage")
    public Map<String, Object> sendMqttMessage(@RequestBody Map<String, Object> map) {
        return deviceInfoService.SendMqttMessage((Long) map.get("did"), (String) map.get("message"));
    }
}
