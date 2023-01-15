package com.wei.mailservice;

import com.wei.mailservice.utils.RedisUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MailServiceApplicationTests {

    private RedisUtil redisUtil;

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }
    @Test
    void contextLoads() {

    }
    @Test
    void redisTest() {
        System.out.println(redisUtil.get("@"));
    }

}
