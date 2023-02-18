package com.wei.devicegateway.server;

import javax.annotation.PostConstruct;

import com.wei.devicegateway.utils.MessageHandler;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class MqttDeviceServer {

    private RabbitTemplate rabbitTemplate;

    @Autowired
    private void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    private void mqttServer() {
        String broker = "tcp://192.168.3.66:1886";
        String topic = "mqtt/weilai";
        String username = "weilai";
        String password = "weilaiPassword";
        String clientid = "client_weilai";
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
                    System.out.println(new String(message.getPayload()));


                    String exchangeName = "device.direct";
                    String routingKey = "mqtt";

                    Map<String, Object> map = MessageHandler.handleMessage(new String(message.getPayload()));

                    if (map != null) {
                        rabbitTemplate.convertAndSend(exchangeName,routingKey,map);
                    }


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
