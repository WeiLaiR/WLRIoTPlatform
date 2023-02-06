package com.wei.mailservice.utils;

import com.wei.mailservice.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.LinkedList;
import java.util.Queue;

@Component
public class SendEmail {

    private JavaMailSenderImpl javaMailSender;

    @Autowired
    public void setJavaMailSender(JavaMailSenderImpl javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    private BuildVCode buildVCode;

    @Autowired
    public void setBuildVCode(BuildVCode buildVCode) {
        this.buildVCode = buildVCode;
    }

    private static Queue<String> queue = new LinkedList<>();

    public static void addQueue(String email) {
        queue.add(email);
    }


    @Scheduled(cron = "0/15 * * * * ?")
    void senEmailUt() {
        while (!queue.isEmpty()) {
            String email = queue.poll();
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            String vCode = buildVCode.buildVCode(email);

            try {
                MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

                messageHelper.setSubject("[验证码]——WLR IoT Platform");
                messageHelper.setText(BuildMail.buildContent(vCode), true);
                messageHelper.setTo(email);
                messageHelper.setFrom("WLR IoT Platform" + '<' + "info@baiblog.top" + '>');

                javaMailSender.send(mimeMessage);

            } catch (Exception e) {
                throw new CustomException(400, "邮件发送失败！请稍后重试或联系管理员。");
            }
        }
    }

}
