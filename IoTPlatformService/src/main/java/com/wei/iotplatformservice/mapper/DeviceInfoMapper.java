package com.wei.iotplatformservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wei.iotplatformservice.pojo.DeviceInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceInfoMapper extends BaseMapper<DeviceInfo> {

    List<DeviceInfo> qInfoListPQbc(String val, Integer start, Integer limit, Long uid);

    Integer qInfoListCountQbc();

    List<DeviceInfo> queryBriefInfoList(Long uid);

    Long queryDeviceIdByToken(String token);

    Integer countByDeviceId(Long uid);

    Integer countByProtocolNotNull(Long uid);

    Long queryUidLong(Long did);

}
