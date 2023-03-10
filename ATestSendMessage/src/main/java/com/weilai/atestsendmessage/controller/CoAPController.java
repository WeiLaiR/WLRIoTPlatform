package com.weilai.atestsendmessage.controller;

import com.weilai.atestsendmessage.coap.CoAPSendMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
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

        List<Map<String, Object>> list = (List)  map.get("list");

        customThreadPool1.execute(() -> CoAPTask(token, list, (boolean)map.getOrDefault("equipment_no", false)));

        map1.put("state", "200");
        map1.put("message", "success");

        return map1;
    }

    @PostMapping("/stopCoap")
    public Map<String, Object> stopCoAP(@RequestBody Map<String, Object> map) {
        Map<String, Object> map1 = new HashMap<>();

        status.remove((String) map.get("token"));

        map1.put("state", "200");
        map1.put("message", "success");

        return map1;
    }

    @GetMapping("/getMap")
    public Map<String, Object> getMap() {
        Map<String, Object> map = new HashMap<>();

        map.put("state", "200");
        map.put("message", "success");
        map.put("data", status);

        return map;
    }


    private void CoAPTask(String token, List<Map<String, Object>> list, boolean EN) {
        Random random = new Random();
        double version = ((int) (random.nextDouble() * 30)) / 10.0 + 0.1;
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
            coAPSendMessage.sendMessage(sb.toString());

            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
