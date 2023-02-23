package com.wei.iotplatformservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wei.iotplatformservice.pojo.DeviceCfg;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceCfgMapper extends BaseMapper<DeviceCfg> {
    List<DeviceCfg> queryDeviceCfgListP(Integer start, Integer limit, Long did);

    Integer queryDeviceCfgCountP();

    List<DeviceCfg> queryBriefCfgList(Long did);

    List<DeviceCfg> queryBriefCfgListAndIsNumber(Long did);

    Boolean queryDeviceIsNumber(Long cid);
}
