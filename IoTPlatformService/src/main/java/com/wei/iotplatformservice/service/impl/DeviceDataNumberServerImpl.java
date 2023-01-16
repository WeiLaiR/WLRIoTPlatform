package com.wei.iotplatformservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wei.iotplatformservice.mapper.DeviceDataNumberMapper;
import com.wei.iotplatformservice.pojo.DeviceDataNumber;
import com.wei.iotplatformservice.service.DeviceDataNumberService;
import org.springframework.stereotype.Service;

@Service
public class DeviceDataNumberServerImpl extends ServiceImpl<DeviceDataNumberMapper, DeviceDataNumber> implements DeviceDataNumberService {
}

