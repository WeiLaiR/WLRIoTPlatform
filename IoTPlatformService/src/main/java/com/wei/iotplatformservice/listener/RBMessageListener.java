package com.wei.iotplatformservice.listener;

import com.wei.iotplatformservice.handler.DeviceMessageHandler;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RBMessageListener {

    private DeviceMessageHandler deviceMessageHandler;
    @Autowired
    public void setDeviceMessageHandler(DeviceMessageHandler deviceMessageHandler) {
        this.deviceMessageHandler = deviceMessageHandler;
    }

    /**
     * 接受MQ消息
     * @param map http信息
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "device.http.queue"),
            exchange = @Exchange(name = "device.direct"),
            key = {"http"}
    ))
    public void ListenDirectExchangeQueueHttpMessage(Map<String, Object> map) {
        deviceMessageHandler.handleMessage(map, "HTTP");
    }

    /**
     * 接受MQ消息
     * @param map mqtt信息
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "device.mqtt.queue1"),
            exchange = @Exchange(name = "device.direct"),
            key = {"mqtt"}
    ))
    public void ListenDirectExchangeQueueMqttMessage(Map<String, Object> map) {
        deviceMessageHandler.handleMessage(map, "MQTT");
    }

    /**
     * 接受MQ消息
     * @param map coap信息
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "device.coap.queue1"),
            exchange = @Exchange(name = "device.direct"),
            key = {"coap"}
    ))
    public void ListenDirectExchangeQueueCoAPMessage(Map<String, Object> map) {
        deviceMessageHandler.handleMessage(map, "CoAP");
    }

}
