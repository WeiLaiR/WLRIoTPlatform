package com.wei.iotplatformservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wei.iotplatformservice.pojo.DeviceData;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceDataMapper extends BaseMapper<DeviceData> {

    List<DeviceData> queryDeviceDataListP(Integer start, Integer limit, Long cid, String startTime, String endTime);

    Integer queryDeviceDataCount();

}
