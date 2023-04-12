package com.wei.devicegateway.listener;


import com.wei.devicegateway.mqtt.MqttSendMessage;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RBMessageListener {


    private MqttSendMessage mqttSendMessage;
    @Autowired
    public void setMqttSendMessage(MqttSendMessage mqttSendMessage) {
        this.mqttSendMessage = mqttSendMessage;
    }

    /**
     * 通过Mqtt发送
     * @param map 发送mqtt信息
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "device.mqtt.queue2"),
            exchange = @Exchange(name = "device.direct"),
            key = {"SenderMqtt"}
    ))
    public void ListenDirectExchangeQueueMqttMessage(Map<String, Object> map) {
        mqttSendMessage.sender((String) map.get("message"), false, (String) map.get("token"));
    }



}
