package com.wei.devicegateway.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/DeviceAPI")
public class HttpDeviceController {

    @PostMapping("/httpTelemetry")
    public Map<String, Object> httpTelemetry(@RequestBody Map<Object, Object> message) {
        Map<String, Object> map = new HashMap<>();

        System.out.println(message);

        map.put("state", "200");

        return map;
    }

}
