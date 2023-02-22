package com.wei.iotplatformservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient("user-service")
public interface UserRemindClient {
    @PutMapping("/insertRemind")
    void insertRemind(@RequestBody Map<String, Object> map);
}
