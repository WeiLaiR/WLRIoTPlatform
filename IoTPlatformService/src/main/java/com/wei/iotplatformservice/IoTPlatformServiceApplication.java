package com.wei.iotplatformservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableDiscoveryClient //注册到服务器中心
@EnableFeignClients //开启Feign
@EnableAsync //开启异步
public class IoTPlatformServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(IoTPlatformServiceApplication.class, args);
    }

}
