package com.wei.iotplatformservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wei.iotplatformservice.exception.CustomException;
import com.wei.iotplatformservice.mapper.DeviceRuleMapper;
import com.wei.iotplatformservice.pojo.DeviceRule;
import com.wei.iotplatformservice.service.DeviceRuleService;
import com.wei.iotplatformservice.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeviceRuleServiceImpl extends ServiceImpl<DeviceRuleMapper, DeviceRule> implements DeviceRuleService {

    private DeviceRuleMapper deviceRuleMapper;

    @Autowired
    public void setDeviceRuleMapper(DeviceRuleMapper deviceRuleMapper) {
        this.deviceRuleMapper = deviceRuleMapper;
    }

    private RedisUtil redisUtil;
    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Override
    public Map<String, Object> newDeviceRule(DeviceRule deviceRule) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            if (deviceRuleMapper.insert(deviceRule) > 0) {
                map.put("status", 200);
                map.put("message", "新增规则成功");
            } else {
                map.put("status", 400);
                map.put("message", "新增规则失败");
            }

        } catch (Exception e) {
            throw new CustomException(400, "出现了未知异常！(DeviceRuleServiceImpl.newDeviceRule)");
        }
        return map;
    }

    @Override
    public Map<String, Object> qDeviceRuleListP(Long cid, Integer start, Integer limit) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            List<DeviceRule> deviceRules = deviceRuleMapper.queryDeviceRuleListP(start, limit, cid);
            Integer total = deviceRuleMapper.queryDeviceRuleCountP();
            map.put("status", 200);
            map.put("message", "查询成功");
            map.put("data", deviceRules);
            map.put("total", total);
        } catch (Exception e) {
            throw new CustomException(400, "出现了未知异常！(DeviceRuleServiceImpl.qDeviceRuleListP)");
        }
        return map;
    }

    @Override
    public Map<String, Object> updateDeviceRule(DeviceRule deviceRule) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            if (deviceRuleMapper.updateById(deviceRule) > 0) {
                map.put("status", 200);
                map.put("message", "更新规则成功");
            } else {
                map.put("status", 400);
                map.put("message", "更新规则失败");
            }

        } catch (Exception e) {
            throw new CustomException(400, "出现了未知异常！(DeviceRuleServiceImpl.updateDeviceRule)");
        }
        return map;
    }

    @Override
    public Map<String, Object> deleteDeviceRule(Long ruleId) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            if (deviceRuleMapper.deleteById(ruleId) > 0) {
                map.put("status", 200);
                map.put("message", "删除规则成功");
            } else {
                map.put("status", 400);
                map.put("message", "删除规则失败");
            }

        } catch (Exception e) {
            throw new CustomException(400, "出现了未知异常！(DeviceRuleServiceImpl.deleteDeviceRule)");
        }
        return map;
    }

    @Override
    public void deleteRedisDeviceRule(Long cId) {
        redisUtil.del(cId + "rule");
    }
}
