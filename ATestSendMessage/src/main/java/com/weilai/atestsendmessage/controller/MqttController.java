package com.weilai.atestsendmessage.controller;

import com.weilai.atestsendmessage.mqtt.MqttSendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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

        List<Map<String, Object>> list = (List) val.get("list");

        customThreadPool1.execute(() -> MqttTask(token, list, (boolean)val.getOrDefault("equipment_no", false)));

        map.put("state", "200");
        map.put("message", "success");

        return map;
    }

    @PostMapping("/stopMqtt")
    public Map<String, Object> stopMqtt(@RequestBody Map<String, Object> val) {
        Map<String, Object> map = new HashMap<>();

        status.remove((String) val.get("token"));

        map.put("state", "200");
        map.put("message", "success");

        return map;
    }

    @GetMapping("/getMap")
    public Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>();

        map.put("state", "200");
        map.put("message", "success");
        map.put("data", status);

        return map;
    }

    private void MqttTask(String token, List<Map<String, Object>> list, boolean EN) {
        Random random = new Random();
        double version = ((int) (random.nextDouble() * 30)) / 10.0 + 0.6;
        int index = 0;
        while (status.getOrDefault(token, false)) {
            StringBuilder sb = new StringBuilder();
            sb.append("token=").append(token);
            sb.append("&version=").append(String.format("%.1f", version));

            if (EN) {
                sb.append("&equipment_no=").append("NO." + index);
                index++;
                index %= 3;
            }

            for (Map<String, Object> val : list) {
                int statVal = 0;
                int endVal = 100;
                if (val.getOrDefault("startVal", null) != null) {
                    statVal = (int) val.get("startVal");
                }

                if (val.getOrDefault("endVal", null) != null) {
                    endVal = (int) val.get("endVal");
                }

                double v = random.nextDouble() * (endVal - statVal);
                v = v + statVal;

                String value = String.format("%.2f", v);
                if (! (Boolean) val.get("isNumber")) {
                    value = "Message" + value;
                }
                sb.append("&").append(val.get("typeName")).append("=").append(value);
            }
            message.publish(sb.toString(), false);

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
