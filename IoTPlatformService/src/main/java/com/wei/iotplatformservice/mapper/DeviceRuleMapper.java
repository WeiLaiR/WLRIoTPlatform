package com.wei.iotplatformservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wei.iotplatformservice.pojo.DeviceRule;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRuleMapper extends BaseMapper<DeviceRule> {

    List<DeviceRule> queryDeviceRuleListP(Integer start, Integer limit, Long cid);

    Integer queryDeviceRuleCountP();

}
