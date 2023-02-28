package com.wei.iotplatformservice.controller;

import com.wei.iotplatformservice.exception.CustomException;
import com.wei.iotplatformservice.service.DeviceCfgService;
import com.wei.iotplatformservice.service.DeviceDataNumberService;
import com.wei.iotplatformservice.service.DeviceDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/platform/deviceData")
public class DeviceDataController {

    DeviceCfgService deviceCfgService;
    @Autowired
    public void setDeviceCfgService(DeviceCfgService deviceCfgService) {
        this.deviceCfgService = deviceCfgService;
    }

    DeviceDataService deviceDataService;
    @Autowired
    public void setDeviceDataService(DeviceDataService deviceDataService) {
        this.deviceDataService = deviceDataService;
    }

    DeviceDataNumberService deviceDataNumberService;
    @Autowired
    public void setDeviceDataNumberService(DeviceDataNumberService deviceDataNumberService) {
        this.deviceDataNumberService = deviceDataNumberService;
    }

    @GetMapping("/listP/{start}/{limit}/{cid}/{startTime}/{endTime}")
    public Map<String, Object> getDataListP(@PathVariable Integer start,
                                            @PathVariable Integer limit,
                                            @PathVariable Long cid,
                                            @PathVariable String startTime,
                                            @PathVariable String endTime) {
        Boolean isNumber = deviceCfgService.queryDeviceIsNumber(cid);
//        System.out.println(startTime + "===================================" + endTime);
        if (isNumber != null) {
            if (start >= 0 && limit > 0) {
                Map<String, Object> map;
                if (isNumber) {
                    map = deviceDataNumberService.queryDeviceDataNumberListP((start - 1) * limit, limit, cid, startTime, endTime);
                    map.put("isNumber", true);
                }else {
                    map = deviceDataService.queryDeviceDataListP((start - 1) * limit, limit, cid, startTime, endTime);
                    map.put("isNumber", false);
                }
                return map;
            } else {
                throw new CustomException(400, "暂不可查询所有内容！");
            }
        } else {
            throw new CustomException(400, "查询失败，发生未知异常！");
        }
    }

    @GetMapping("/value/{cid}/{startTime}/{endTime}")
    public Map<String, Object> getDataValue(@PathVariable Long cid,
                                            @PathVariable String startTime,
                                            @PathVariable String endTime) {
        Boolean isNumber = deviceCfgService.queryDeviceIsNumber(cid);
        if (isNumber != null) {
            Map<String, Object> map;
            if (isNumber) {
                map = deviceDataNumberService.queryDeviceDataNumberValue(cid, startTime, endTime);
            }else {
                throw new CustomException(400, "该数据不可转化为数据摘要图！");
            }
            return map;
        } else {
            throw new CustomException(400, "查询失败，发生未知异常！");
        }
    }
}
