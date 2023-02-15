package com.weilai.atestsendmessage.controller;

import com.weilai.atestsendmessage.coap.CoAPSendMessage;
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
@RequestMapping("/coap")
public class CoAPController {

    private CoAPSendMessage coAPSendMessage;

    @Autowired
    public void setCoAPSendMessage(CoAPSendMessage coAPSendMessage) {
        this.coAPSendMessage = coAPSendMessage;
    }

    private ThreadPoolTaskExecutor customThreadPool1;

    @Autowired()
    public void setCustomThreadPool(ThreadPoolTaskExecutor customThreadPool1) {
        this.customThreadPool1 = customThreadPool1;
    }

    private static final Map<String, Boolean> status = new HashMap<>();


    @PostMapping("/addCoap")
    public Map<String, Object> addCoAP(@RequestBody Map<String, Object> map) {
        Map<String, Object> map1 = new HashMap<>();

        String token = (String) map.get("token");
        status.put(token, true);

        customThreadPool1.execute(() -> CoAPTask(token, (Map<String, Boolean>) map.get("map")));

        map1.put("state", "200");
        map1.put("message", "success");

        return map1;
    }

    @PostMapping("/stopCoap")
    public Map<String, Object> stopCoAP(@RequestBody Map<String, Object> map) {
        Map<String, Object> map1 = new HashMap<>();

        status.put((String) map.get("token"), false);

        map1.put("state", "200");
        map1.put("message", "success");

        return map1;
    }


    private void CoAPTask(String token, Map<String, Boolean> map) {
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
            coAPSendMessage.sendMessage(sb.toString());

            try {
                Thread.sleep(3600);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
