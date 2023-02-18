package com.wei.devicegateway.utils;

import java.util.HashMap;
import java.util.Map;

public class MessageHandler {

    public static Map<String, Object> handleMessage(String message) {
        Map<String, Object> map = new HashMap<>();

        String[] strings = message.split("&");

        for (String string : strings) {
            String[] strings1 = string.split("=");
            map.put(strings1[0], strings1[1]);
        }

        if (map.getOrDefault("token", null) == null) {
            return null;
        }else {
            String token = (String) map.get("token");
            if (token.length() != 22) {
                return null;
            }
        }

        return map;
    }


}
