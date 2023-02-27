package com.weilai.atestsendmessage.mqtt;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class MqttSendMessage {

    private final String broker = "tcp://192.168.3.66:1886";
    private final String topic = "mqtt/wlr";
    private final String username = "wlruser";
    private final String password = "wlriot";
    private final String clientid = "publish_client_token";
    private final int qos = 1;


    /**
     * 客户端对象
     */
    private MqttClient client;

    @PostConstruct
    private void init() {
        try {
            client = new MqttClient(broker, clientid, new MemoryPersistence());
            // 连接参数
            MqttConnectOptions options = new MqttConnectOptions();
            // 设置用户名和密码
            options.setUserName(username);
            options.setPassword(password.toCharArray());
            options.setConnectionTimeout(5);
            options.setKeepAliveInterval(60);
            options.setAutomaticReconnect(true);
            options.setKeepAliveInterval(20);
            //设置遗嘱消息的话题，若客户端和服务器之间的连接意外断开，服务器将发布客户端的遗嘱信息
            options.setWill("willTopic",(clientid + "与服务器断开连接").getBytes(),0,false);
            //设置回调
            client.setCallback(new MqttProviderCallBack());
            client.connect(options);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void publish(String message, boolean retained) {
        MqttMessage mqttMessage = new MqttMessage();
        mqttMessage.setQos(qos);
        mqttMessage.setRetained(retained);
        mqttMessage.setPayload(message.getBytes());
        //主题的目的地，用于发布/订阅信息
        MqttTopic mqttTopic = client.getTopic(topic);
        //提供一种机制来跟踪消息的传递进度
        //用于在以非阻塞方式（在后台运行）执行发布是跟踪消息的传递进度
        MqttDeliveryToken token;
        try {
            //将指定消息发布到主题，但不等待消息传递完成，返回的token可用于跟踪消息的传递状态
            //一旦此方法干净地返回，消息就已被客户端接受发布，当连接可用，将在后台完成消息传递。
            token = mqttTopic.publish(mqttMessage);
            token.waitForCompletion();
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }



}
