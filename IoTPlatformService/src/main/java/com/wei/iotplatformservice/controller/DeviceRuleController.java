package com.wei.iotplatformservice.controller;

import com.wei.iotplatformservice.exception.CustomException;
import com.wei.iotplatformservice.pojo.DeviceRule;
import com.wei.iotplatformservice.service.DeviceRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/platform/deviceRule")
public class DeviceRuleController {

    private DeviceRuleService deviceRuleService;
    @Autowired
    public void setDeviceRuleService(DeviceRuleService deviceRuleService) {
        this.deviceRuleService = deviceRuleService;
    }

    @GetMapping("/listP/{start}/{limit}/{cid}")
    public Map<String, Object> getDeviceRuleListP(@PathVariable Integer start, @PathVariable Integer limit, @PathVariable Long cid) {
        if (start >= 0 && limit > 0) {
             return deviceRuleService.qDeviceRuleListP(cid, (start - 1) * limit, limit);
        } else {
             throw new CustomException(400, "暂不可查询所有内容！");
        }
    }

    @PutMapping("/add")
    public Map<String, Object> newDeviceRule(@RequestBody DeviceRule deviceRule) {
        return deviceRuleService.newDeviceRule(deviceRule);
    }

    @PostMapping("/update")
    public Map<String, Object> updateDeviceRule(@RequestBody DeviceRule deviceRule) {
        return deviceRuleService.updateDeviceRule(deviceRule);
    }

    @DeleteMapping("/delete/{ruleId}")
    public Map<String, Object> deleteDeviceRule(@PathVariable Long ruleId) {
        return deviceRuleService.deleteDeviceRule(ruleId);
    }

    @DeleteMapping("/deleteRedisRule/{cid}")
    public void deleteRedisRule(@PathVariable Long cid) {
        deviceRuleService.deleteRedisDeviceRule(cid);
    }


}
