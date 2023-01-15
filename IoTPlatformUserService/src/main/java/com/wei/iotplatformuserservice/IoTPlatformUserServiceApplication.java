package com.wei.iotplatformuserservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling //开启定时任务
@SpringBootApplication
public class IoTPlatformUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(IoTPlatformUserServiceApplication.class, args);
    }

}
