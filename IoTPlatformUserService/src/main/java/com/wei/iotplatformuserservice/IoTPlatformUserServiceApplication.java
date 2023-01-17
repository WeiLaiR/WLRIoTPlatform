package com.wei.iotplatformuserservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling //开启定时任务
@EnableDiscoveryClient //注册到服务器中心
@EnableFeignClients //开启Feign
@EnableAsync //开启异步
@SpringBootApplication
public class IoTPlatformUserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(IoTPlatformUserServiceApplication.class, args);
    }

}
