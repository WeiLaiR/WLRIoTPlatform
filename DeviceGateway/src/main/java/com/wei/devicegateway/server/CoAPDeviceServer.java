package com.wei.devicegateway.server;

import javax.annotation.PostConstruct;

import com.wei.devicegateway.utils.MessageHandler;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CoAPDeviceServer {

    private RabbitTemplate rabbitTemplate;

    @Autowired
    private void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    private void coapServer() {

        CoapServer coapServer = new CoapServer(6667);
        coapServer.add(new CoapResource("coapTelemetry") {
            @Override
            public void handlePOST(CoapExchange exchange) {
                String message = exchange.getRequestText();
                System.out.println(message);

                String exchangeName = "device.direct";
                String routingKey = "coap";

                Map<String, Object> map = MessageHandler.handleMessage(message);

                if (map != null) {
                    rabbitTemplate.convertAndSend(exchangeName,routingKey,map);
                    exchange.respond(CoAP.ResponseCode.CONTENT, "SUCCESS");
                }else {
                    exchange.respond(CoAP.ResponseCode.CONTENT, "ERROR");
                }


            }
        });
        coapServer.start();
    }

    @Async
    @PostConstruct
    public void startCoapServer() {
        coapServer();

        System.out.println("===========================CoAP服务器已开启===========================");
    }
}
