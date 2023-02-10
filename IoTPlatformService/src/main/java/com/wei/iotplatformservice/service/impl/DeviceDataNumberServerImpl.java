package com.wei.iotplatformservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wei.iotplatformservice.mapper.DeviceDataNumberMapper;
import com.wei.iotplatformservice.pojo.DeviceDataNumber;
import com.wei.iotplatformservice.service.DeviceDataNumberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DeviceDataNumberServerImpl extends ServiceImpl<DeviceDataNumberMapper, DeviceDataNumber> implements DeviceDataNumberService {
    DeviceDataNumberMapper deviceDataNumberMapper;
    @Autowired
    public void setDeviceDataNumberMapper(DeviceDataNumberMapper deviceDataNumberMapper) {
        this.deviceDataNumberMapper = deviceDataNumberMapper;
    }

    @Override
    public Map<String, Object> queryDeviceDataNumberListP(Integer start, Integer limit, Long cid, String startTime, String endTime) {
        HashMap<String, Object> map = new HashMap<>();

        List<DeviceDataNumber> deviceDataNumbers = deviceDataNumberMapper.queryDeviceDataNumberListP(start, limit, cid, startTime, endTime);
        Integer count = deviceDataNumberMapper.queryDeviceDataNumberCount();

        map.put("status", 200);
        map.put("message", "查询成功");
        map.put("data", deviceDataNumbers);
        map.put("total", count);

        return map;
    }

    @Override
    public Map<String, Object> queryDeviceDataNumberValue(Long cid, String startTime, String endTime) {
        List<Double> list = deviceDataNumberMapper.queryDeviceDataNumberValue(cid, startTime, endTime);
        Map<String, Object> map = new HashMap<>();
        map.put("status", 200);
        map.put("message", "查询成功");
        if (list.size() < 20) {
            map.put("data", list);
        } else {
            double temp = list.size() / 20.0;
            List<Double> value = new ArrayList<>();
            double avg = 0;
            int index = 0;
            for (int i = 0; i < list.size(); i++) {
                if (20 - value.size() >= list.size() - i) {
                    if (avg != 0) {
                        value.add(((int) (avg / index * 100) / 100.0));
                        avg = 0;
                        index = 0;
                    }
                    value.add(list.get(i));
                } else {
                    avg += list.get(i);
                    index ++;
                    if (index >= temp) {
                        value.add(((int) (avg / index * 100) / 100.0));
                        avg = 0;
                        index = 0;
                    }
                }
            }
            map.put("data", value);
        }
        return map;
    }
}

