package com.wei.iotplatformuserservice;

import com.wei.iotplatformuserservice.mapper.LoginMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IoTPlatformUserServiceApplicationTests {

    @Autowired
    private LoginMapper loginMapper;

    @Test
    void contextLoads() {
    }

    @Test
    void Test1() {
        System.out.println(loginMapper.queryLogin("1@a.com"));
    }

}
