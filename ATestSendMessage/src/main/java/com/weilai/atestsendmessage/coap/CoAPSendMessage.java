package com.weilai.atestsendmessage.coap;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.elements.exception.ConnectorException;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class CoAPSendMessage {
    private final String URL = "coap://localhost:6667/coapTelemetry";

    private CoapClient client;

    @PostConstruct
    private void init(){
        client = new CoapClient(URL);
    }
    public void sendMessage(String message) {
        try {
            client.post(message, MediaTypeRegistry.TEXT_PLAIN);
        } catch (ConnectorException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
