package com.wei.devicegateway.server;

import javax.annotation.PostConstruct;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.springframework.stereotype.Component;

@Component
public class CoAPDeviceServer {
    private static void coapServer() {
        CoapServer coapServer = new CoapServer(6667);
        coapServer.add(new CoapResource("coapTelemetry") {
            @Override
            public void handlePOST(CoapExchange exchange) {
                String requestText = exchange.getRequestText();
                System.out.println(requestText);
                exchange.respond(CoAP.ResponseCode.CONTENT, "OKK");
            }
        });
        coapServer.start();
    }

    @PostConstruct
    public void startCoapServer() {
        new Thread(CoAPDeviceServer::coapServer).start();

        System.out.println("===========================CoAP服务器已开启===========================");
    }
}
