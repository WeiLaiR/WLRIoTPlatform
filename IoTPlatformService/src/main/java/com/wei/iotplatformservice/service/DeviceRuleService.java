package com.wei.iotplatformservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wei.iotplatformservice.pojo.DeviceRule;

import java.util.Map;

public interface DeviceRuleService extends IService<DeviceRule> {

    /**
     * 新增设备规则
     *
     * @param deviceRule 设备规则
     * @return 状态值
     */
    Map<String, Object> newDeviceRule(DeviceRule deviceRule);

    Map<String, Object> qDeviceRuleListP(Long cid, Integer start, Integer limit);

    Map<String, Object> updateDeviceRule(DeviceRule deviceRule);

    Map<String, Object> deleteDeviceRule(Long ruleId);

    void deleteRedisDeviceRule(Long cId);
}
