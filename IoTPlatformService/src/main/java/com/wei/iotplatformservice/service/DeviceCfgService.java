package com.wei.iotplatformservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wei.iotplatformservice.pojo.DeviceCfg;

import java.util.Map;

public interface DeviceCfgService extends IService<DeviceCfg> {

    Map<String, Object> queryDeviceCfgListP(Integer start, Integer limit, Long did);

    Map<String, Object> queryBriefCfgList(Long did);

    Boolean queryDeviceIsNumber(Long cid);
}
