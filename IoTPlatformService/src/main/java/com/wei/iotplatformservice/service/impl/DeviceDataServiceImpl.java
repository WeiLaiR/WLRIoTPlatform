package com.wei.iotplatformservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wei.iotplatformservice.mapper.DeviceDataMapper;
import com.wei.iotplatformservice.pojo.DeviceData;
import com.wei.iotplatformservice.service.DeviceDataService;
import org.springframework.stereotype.Service;

@Service
public class DeviceDataServiceImpl extends ServiceImpl<DeviceDataMapper, DeviceData> implements DeviceDataService {

}
