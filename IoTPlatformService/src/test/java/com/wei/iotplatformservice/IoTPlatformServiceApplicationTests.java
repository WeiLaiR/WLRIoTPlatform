package com.wei.iotplatformservice;

import com.wei.iotplatformservice.mapper.DeviceCfgMapper;
import com.wei.iotplatformservice.mapper.DeviceDataMapper;
import com.wei.iotplatformservice.mapper.DeviceDataNumberMapper;
import com.wei.iotplatformservice.mapper.DeviceInfoMapper;
import com.wei.iotplatformservice.pojo.DeviceCfg;
import com.wei.iotplatformservice.pojo.DeviceData;
import com.wei.iotplatformservice.pojo.DeviceDataNumber;
import com.wei.iotplatformservice.pojo.DeviceInfo;
import com.wei.iotplatformservice.utils.DeviceTokenUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Random;

@SpringBootTest
class IoTPlatformServiceApplicationTests {

    @Autowired
    DeviceInfoMapper deviceInfoMapper;

    @Autowired
    DeviceCfgMapper deviceCfgMapper;

    @Autowired
    DeviceDataNumberMapper deviceDataNumberMapper;

    @Autowired
    DeviceDataMapper deviceDataMapper;
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

    @Test
    void Test2() {
        for (int i = 0; i < 100; i++) {
            DeviceCfg deviceCfg = new DeviceCfg();
            deviceCfg.setDeviceId(1675915572383001L);
            deviceCfg.setTypeName("typeName" + i);
            deviceCfg.setIsNumber(i % 2 == 0);
            deviceCfgMapper.insert(deviceCfg);
        }
    }

    @Test
    void Test3() {
        for (int i = 0; i < 300; i++) {
            DeviceData deviceData = new DeviceData();
            deviceData.setDeviceCfgId(1676011054947002L);
            deviceData.setData("data" + i);
            deviceDataMapper.insert(deviceData);
        }
    }

    @Test
    void Test4() {
        Random random = new Random();
        for (int i = 0; i < 300; i++) {
            DeviceDataNumber dataNumber = new DeviceDataNumber();
            dataNumber.setDeviceCfgId(1676011054947001L);
            dataNumber.setData(((int) (random.nextDouble() * 10000) / 100.0));
            deviceDataNumberMapper.insert(dataNumber);
        }
    }



}
