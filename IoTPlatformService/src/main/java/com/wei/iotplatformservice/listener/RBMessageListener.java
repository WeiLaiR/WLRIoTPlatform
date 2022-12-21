package com.wei.iotplatformservice.listener;

import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class RBMessageListener {


    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "device.http.queue"),
            exchange = @Exchange(name = "device.direct", type = ExchangeTypes.DIRECT),
            key = {"http"}
    ))
    public void ListenDirectExchangeQueueHttpMessage(String msg) {
        System.out.println("接收到HttpQueue的消息" + msg);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "device.mqtt.queue1"),
            exchange = @Exchange(name = "device.direct", type = ExchangeTypes.DIRECT),
            key = {"mqtt"}
    ))
    public void ListenDirectExchangeQueueMqttMessage(String msg) {
        System.out.println("接收到MqttQueue的消息" + msg);
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "device.coap.queue1"),
            exchange = @Exchange(name = "device.direct", type = ExchangeTypes.DIRECT),
            key = {"coap"}
    ))
    public void ListenDirectExchangeQueueCoAPMessage(String msg) {
        System.out.println("接收到CoAPQueue的消息" + msg);
    }

}
