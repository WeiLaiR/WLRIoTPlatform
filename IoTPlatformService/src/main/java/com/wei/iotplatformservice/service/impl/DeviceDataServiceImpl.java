package com.wei.iotplatformservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wei.iotplatformservice.mapper.DeviceDataMapper;
import com.wei.iotplatformservice.pojo.DeviceData;
import com.wei.iotplatformservice.service.DeviceDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DeviceDataServiceImpl extends ServiceImpl<DeviceDataMapper, DeviceData> implements DeviceDataService {

    DeviceDataMapper deviceDataMapper;
    @Autowired
    public void setDeviceDataMapper(DeviceDataMapper deviceDataMapper) {
        this.deviceDataMapper = deviceDataMapper;
    }

    @Override
    public Map<String, Object> queryDeviceDataListP(Integer start, Integer limit, Long cid, String startTime, String endTime) {
        HashMap<String, Object> map = new HashMap<>();

        List<DeviceData> deviceData = deviceDataMapper.queryDeviceDataListP(start, limit, cid, startTime, endTime);
        Integer count = deviceDataMapper.queryDeviceDataCount();

        map.put("status", 200);
        map.put("message", "查询成功");
        map.put("data", deviceData);
        map.put("total", count);

        return map;
    }
}
