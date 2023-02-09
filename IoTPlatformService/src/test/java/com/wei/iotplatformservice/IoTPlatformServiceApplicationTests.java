package com.wei.iotplatformservice;

import com.wei.iotplatformservice.mapper.DeviceInfoMapper;
import com.wei.iotplatformservice.pojo.DeviceInfo;
import com.wei.iotplatformservice.utils.DeviceTokenUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IoTPlatformServiceApplicationTests {

    @Autowired
    DeviceInfoMapper deviceInfoMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void Test1() {
        for (int i = 0; i < 9999; i++) {
            long val = 2837548679L;
            DeviceInfo deviceInfo = new DeviceInfo();

            String deviceToken = DeviceTokenUtil.BuildDeviceToken();

            deviceInfo.setDeviceToken(deviceToken);

            deviceInfo.setUid(1675673716886001L);

            deviceInfo.setDeviceName( i + "name");

            deviceInfo.setDescription(val + i + "scr");

            deviceInfoMapper.insert(deviceInfo);
        }
    }

}
