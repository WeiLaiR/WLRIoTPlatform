package com.weilai.atestsendmessage.mqtt;

import javax.annotation.PostConstruct;


import com.weilai.atestsendmessage.controller.MqttController;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;


@Component
public class MqttDeviceServer {


    private void mqttServer() {
        String broker = "tcp://10.162.4.148:1886";
        String topic = "re/#";
        String username = "wlruser";
        String password = "wlriot";
        String clientid = "client_wlruser";
        int qos = 1;

        try {
            MqttClient client = new MqttClient(broker, clientid, new MemoryPersistence());
            // 连接参数
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(username);
            options.setPassword(password.toCharArray());
            options.setConnectionTimeout(5);
            options.setKeepAliveInterval(60);
            options.setAutomaticReconnect(true);
            // 设置回调
            client.setCallback(new MqttCallback() {

                public void connectionLost(Throwable cause) {
                    System.out.println("connectionLost: " + cause.getMessage());
                }

                public void messageArrived(String topic, MqttMessage message) {
//                    System.out.println("topic: " + topic);
//                    System.out.println("Qos: " + message.getQos());
                    //消息
//                    System.out.println(new String(message.getPayload()));



                    String exchangeName = "device.direct";
                    String routingKey = "mqtt";

                    String msg = new String(message.getPayload());

                    HashMap<String, Object> map = new HashMap<>();
                    SimpleDateFormat bjSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");     // 北京
                    bjSdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));  // 设置北京时区
                    map.put("Date", bjSdf.format(new Date()));
                    map.put("topic", topic);
                    map.put("message", msg);

                    MqttController.setQueue(map);

                }

                public void deliveryComplete(IMqttDeliveryToken token) {
                    System.out.println("deliveryComplete---------" + token.isComplete());
                }

            });
            client.connect(options);
            client.subscribe(topic, qos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Async
    @PostConstruct
    public void startMqttServer() {
        mqttServer();

        System.out.println("===========================MQTT服务器已开启===========================");
    }


}
