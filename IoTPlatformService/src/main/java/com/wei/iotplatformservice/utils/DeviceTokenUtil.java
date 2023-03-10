package com.wei.iotplatformservice.utils;

import java.util.UUID;

public class DeviceTokenUtil {

    public static String[] chars = new String[]{"a", "b", "c", "d", "e", "f",
            "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
            "t", "u", "v", "w", "x", "y", "z", "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "-", "_"};

    /**
     * 生成设备token （简短uuid生成器）
     * @return 设备token
     */
    public static String BuildDeviceToken() {
        StringBuilder shortBuffer = new StringBuilder();
        String uuid = UUID.randomUUID().toString().replace("-", "");
        // 每3个十六进制字符转换成为2个字符
        for (int i = 0; i < 10; i++) {
            String str = uuid.substring(i * 3, i * 3 + 3);
            int x = Integer.parseInt(str, 16);      // 转成十六进制
            shortBuffer.append(chars[x / 0x40]);    // 除64得到前面6个二进制数的
            shortBuffer.append(chars[x % 0x40]);    // 对64求余得到后面6个二进制数1
        }
        // 加上后面两个没有改动的
        shortBuffer.append(uuid.charAt(30));
        shortBuffer.append(uuid.charAt(31));
        // 生成的Token共22位
        return shortBuffer.toString();
    }
}
