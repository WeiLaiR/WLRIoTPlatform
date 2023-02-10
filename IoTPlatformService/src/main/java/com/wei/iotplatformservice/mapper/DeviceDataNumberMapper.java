package com.wei.iotplatformservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wei.iotplatformservice.pojo.DeviceDataNumber;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceDataNumberMapper extends BaseMapper<DeviceDataNumber> {
    List<DeviceDataNumber> queryDeviceDataNumberListP(Integer start, Integer limit, Long cid, String startTime, String endTime);

    Integer queryDeviceDataNumberCount();

    List<Double> queryDeviceDataNumberValue(Long cid, String startTime, String endTime);
}
