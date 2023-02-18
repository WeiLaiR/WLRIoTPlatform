package com.wei.devicegateway.controller;

import com.wei.devicegateway.utils.MessageHandler;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/DeviceAPI")
public class HttpDeviceController {

    private RabbitTemplate rabbitTemplate;

    @Autowired
    private void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PostMapping("/httpTelemetry")
    public Map<String, Object> httpTelemetry(@RequestBody String message) {
        Map<String, Object> map = new HashMap<>();

        System.out.println(message);

        String exchangeName = "device.direct";
        String routingKey = "http";

        Map<String, Object> map1 = MessageHandler.handleMessage(message);

        if (map1 != null) {
            rabbitTemplate.convertAndSend(exchangeName,routingKey,map1);

            map.put("state", "200");
        }else {
            map.put("status", "400");
        }

        return map;
    }

}
