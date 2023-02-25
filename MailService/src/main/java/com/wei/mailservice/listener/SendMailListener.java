package com.wei.mailservice.listener;

import com.wei.mailservice.utils.SendEmail;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class SendMailListener {

    private ThreadPoolTaskExecutor customThreadPool1;

    @Autowired
    public void setCustomThreadPool1(ThreadPoolTaskExecutor customThreadPool1) {
        this.customThreadPool1 = customThreadPool1;
    }

    private SendEmail sendEmail;
    @Autowired
    public void setSendEmail(SendEmail sendEmail) {
        this.sendEmail = sendEmail;
    }

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "platform.mail.queue"),
            exchange = @Exchange(name = "mail.direct"),
            key = {"send"}
    ))
    public void ListenSendMailExchangeQueue(Map<String, Object> map) {
        customThreadPool1.execute(() -> {
            sendEmail.sendWarnEmail(map);
        });
    }
}
