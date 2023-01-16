package com.wei.iotplatformservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wei.iotplatformservice.mapper.DeviceInfoMapper;
import com.wei.iotplatformservice.pojo.DeviceInfo;
import com.wei.iotplatformservice.service.DeviceInfoService;
import org.springframework.stereotype.Service;

@Service
public class DeviceInfoServiceImpl extends ServiceImpl<DeviceInfoMapper, DeviceInfo> implements DeviceInfoService {

}
