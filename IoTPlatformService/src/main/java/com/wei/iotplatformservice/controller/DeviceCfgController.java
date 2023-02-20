package com.wei.iotplatformservice.controller;

import com.wei.iotplatformservice.exception.CustomException;
import com.wei.iotplatformservice.pojo.DeviceCfg;
import com.wei.iotplatformservice.service.DeviceCfgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/platform/deviceCfg")
public class DeviceCfgController {
    private DeviceCfgService deviceCfgService;

    @Autowired
    public void setDeviceCfgService(DeviceCfgService deviceCfgService) {
        this.deviceCfgService = deviceCfgService;
    }

    @GetMapping("/listP/{start}/{limit}/{did}")
    public Map<String, Object> getDeviceCfgListP(@PathVariable Integer start, @PathVariable Integer limit, @PathVariable Long did) {
        if (start >= 0 && limit > 0) {
            return deviceCfgService.queryDeviceCfgListP((start - 1) * limit, limit, did);
        } else {
            throw new CustomException(400, "暂不可查询所有内容！");
        }
    }

    @GetMapping("/briefList/{did}")
    public Map<String, Object> getBriefCfgList(@PathVariable Long did) {
        return deviceCfgService.queryBriefCfgList(did);
    }

    @PutMapping("/newDeviceCfg")
    public Map<String, Object> newDeviceCfg(@RequestBody DeviceCfg deviceCfg) {
        return deviceCfgService.newDeviceCfg(deviceCfg);
    }

    @PostMapping("/updateDeviceCfg")
    public Map<String, Object> updateDeviceCfg(@RequestBody DeviceCfg deviceCfg) {
        return deviceCfgService.updateDeviceCfg(deviceCfg);
    }

    @DeleteMapping("/deleteDeviceCfg/{cid}")
    public Map<String, Object> deleteDeviceCfg(@PathVariable Long cid) {
        return deviceCfgService.deleteDeviceCfg(cid);
    }


}
