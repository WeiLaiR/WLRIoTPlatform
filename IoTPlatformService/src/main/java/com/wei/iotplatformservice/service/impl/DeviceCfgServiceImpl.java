package com.wei.iotplatformservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wei.iotplatformservice.exception.CustomException;
import com.wei.iotplatformservice.mapper.DeviceCfgMapper;
import com.wei.iotplatformservice.mapper.DeviceDataMapper;
import com.wei.iotplatformservice.mapper.DeviceDataNumberMapper;
import com.wei.iotplatformservice.pojo.DeviceCfg;
import com.wei.iotplatformservice.pojo.DeviceData;
import com.wei.iotplatformservice.pojo.DeviceDataNumber;
import com.wei.iotplatformservice.service.DeviceCfgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeviceCfgServiceImpl extends ServiceImpl<DeviceCfgMapper, DeviceCfg> implements DeviceCfgService {

    private DeviceCfgMapper deviceCfgMapper;

    @Autowired
    public void setDeviceCfgMapper(DeviceCfgMapper deviceCfgMapper) {
        this.deviceCfgMapper = deviceCfgMapper;
    }

    DeviceDataMapper deviceDataMapper;
    @Autowired
    public void setDeviceDataMapper(DeviceDataMapper deviceDataMapper) {
        this.deviceDataMapper = deviceDataMapper;
    }
    DeviceDataNumberMapper deviceDataNumberMapper;
    @Autowired
    public void setDeviceDataNumberMapper(DeviceDataNumberMapper deviceDataNumberMapper) {
        this.deviceDataNumberMapper = deviceDataNumberMapper;
    }

    @Override
    public Map<String, Object> queryDeviceCfgListP(Integer start, Integer limit, Long did) {
        Map<String, Object> map = new HashMap<>();

        List<DeviceCfg> deviceCfg = deviceCfgMapper.queryDeviceCfgListP(start, limit, did);
        Integer count = deviceCfgMapper.queryDeviceCfgCountP();

        map.put("status", 200);
        map.put("message", "查询成功");
        map.put("data", deviceCfg);
        map.put("total", count);

        return map;
    }

    @Override
    public Map<String, Object> queryBriefCfgList(Long did) {
        Map<String, Object> map = new HashMap<>();

        List<DeviceCfg> cfgS = deviceCfgMapper.queryBriefCfgList(did);
        ArrayList<Map<String, Object>> maps = new ArrayList<>();

        for (DeviceCfg cfg : cfgS) {
            HashMap<String, Object> map1 = new HashMap<>();
            map1.put("text", cfg.getTypeNickName() + "-" + cfg.getTypeName() + " - " + cfg.getDeviceCfgId());
            map1.put("value", cfg.getDeviceCfgId());
            maps.add(map1);
        }

        map.put("status", 200);
        map.put("message", "查询成功");
        map.put("data", maps);

        return map;
    }

    @Override
    public Map<String, Object> queryBriefCfgListAndIsNumber(Long did) {

        Map<String, Object> map = new HashMap<>();

        List<DeviceCfg> cfgS = deviceCfgMapper.queryBriefCfgListAndIsNumber(did);
        ArrayList<Map<String, Object>> maps = new ArrayList<>();

        for (DeviceCfg cfg : cfgS) {
            HashMap<String, Object> map1 = new HashMap<>();
            map1.put("text", cfg.getTypeNickName() + "-" + cfg.getTypeName() + " - " + cfg.getDeviceCfgId());
            map1.put("value", cfg.getDeviceCfgId());
            map1.put("isNumber", cfg.getIsNumber());
            maps.add(map1);
        }

        map.put("status", 200);
        map.put("message", "查询成功");
        map.put("data", maps);

        return map;
    }

    @Override
    public Boolean queryDeviceIsNumber(Long cid) {
        return deviceCfgMapper.queryDeviceIsNumber(cid);
    }

    @Override
    public Map<String, Object> newDeviceCfg(DeviceCfg deviceCfg) {
        Map<String, Object> map = new HashMap<>();

        try {
            if (deviceCfgMapper.insert(deviceCfg) > 0) {
                map.put("status", 200);
                map.put("message", "新增成功");
            } else {
                map.put("status", 400);
                map.put("message", "新增失败");
            }
        }catch (Exception e) {
            throw new CustomException(400, "出现了未知异常！(DeviceCfgNEW)");
        }

        return map;
    }

    @Override
    public Map<String, Object> updateDeviceCfg(DeviceCfg deviceCfg) {
        Map<String, Object> map = new HashMap<>();

        try {
            if (deviceCfgMapper.updateById(deviceCfg) > 0) {
                map.put("status", 200);
                map.put("message", "修改成功");
            } else {
                map.put("status", 400);
                map.put("message", "修改失败");
            }
        }catch (Exception e) {
            throw new CustomException(400, "出现了未知异常！(DeviceCfgUPDATE)");
        }

        return map;
    }

    @Override
    public Map<String, Object> deleteDeviceCfg(Long cid) {
        Map<String, Object> map = new HashMap<>();

        boolean sign = false;

        try {
            DeviceCfg deviceCfg = deviceCfgMapper.selectById(cid);

            if (deviceCfg.getIsNumber()) {
                if (deviceDataNumberMapper.delete(new QueryWrapper<DeviceDataNumber>().eq("device_cfg_id", deviceCfg.getDeviceCfgId())) < 0) {
                    sign = true;
                }
            }else {
                if (deviceDataMapper.delete(new QueryWrapper<DeviceData>().eq("device_cfg_id", deviceCfg.getDeviceCfgId())) < 0) {
                    sign = true;
                }
            }

            if (deviceCfgMapper.deleteById(cid) < 0) {
                sign = true;
            }
        }catch (Exception e) {
            throw new CustomException(400, "出现了未知异常！(DeviceCfgDELETE)");
        }

        if (sign) {
            throw new CustomException(400, "出现了未知异常！(DeviceCfgDelete)");
        }

        map.put("status", 200);
        map.put("message", "配置删除成功!");

        return map;
    }


}
