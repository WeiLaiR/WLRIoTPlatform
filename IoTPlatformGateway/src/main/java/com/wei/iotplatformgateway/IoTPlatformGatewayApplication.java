package com.wei.iotplatformgateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient //注册到服务器中心
public class IoTPlatformGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(IoTPlatformGatewayApplication.class, args);
    }

}
