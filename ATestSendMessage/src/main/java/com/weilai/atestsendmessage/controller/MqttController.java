package com.weilai.atestsendmessage.controller;

import com.weilai.atestsendmessage.mqtt.MqttSendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@RestController
@RequestMapping("/mqtt")
public class MqttController {

    private ThreadPoolTaskExecutor customThreadPool1;

    @Autowired()
    public void setCustomThreadPool(ThreadPoolTaskExecutor customThreadPool1) {
        this.customThreadPool1 = customThreadPool1;
    }

    private MqttSendMessage message;

    @Autowired
    public void setMessage(MqttSendMessage message) {
        this.message = message;
    }

    private static final Map<String, Boolean> status = new HashMap<>();

    @PostMapping("/addMqtt")
    public Map<String, Object> addMqtt(@RequestBody Map<String, Object> val) {
        Map<String, Object> map = new HashMap<>();

        String token = (String) val.get("token");
        status.put(token, true);

        customThreadPool1.execute(() -> MqttTask(token, (Map<String, Boolean>) val.get("map")));

        map.put("state", "200");
        map.put("message", "success");

        return map;
    }

    @PostMapping("/stopMqtt")
    public Map<String, Object> stopMqtt(@RequestBody Map<String, Object> val) {
        Map<String, Object> map = new HashMap<>();

        status.put((String) val.get("token"), false);

        map.put("state", "200");
        map.put("message", "success");

        return map;
    }

    private void MqttTask(String token, Map<String, Boolean> map) {
        Random random = new Random();
        while (status.getOrDefault(token, true)) {
            StringBuilder sb = new StringBuilder();
            sb.append("token=").append(token);
            for (String key : map.keySet()) {
                double v = ((int) (random.nextDouble() * 10000)) / 100.0;
                String value = String.valueOf(v);
                if (!map.get(key)) {
                    value = "Message" + value;
                }
                sb.append("&").append(key).append("=").append(value);
            }
            message.publish(sb.toString(), false);

            try {
                Thread.sleep(3600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
