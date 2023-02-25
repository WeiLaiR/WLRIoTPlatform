package com.wei.mailservice.utils;

import com.wei.mailservice.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Map;

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


    public void senEmailUt(String email) {
        Date date = new Date();
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
        Date date1 = new Date();
        System.out.println(date + "==================" + date1);
    }


    public void sendWarnEmail(Map<String, Object> map) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        StringBuilder sb = new StringBuilder();
        sb.append("您好，ID为 ").append(map.get("uid")).append(" 的用户！").append(map.get("message"));
        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

            messageHelper.setSubject("[警告]——WLR IoT Platform");
            messageHelper.setText(BuildMail.buildContent1(sb.toString()), true);
            messageHelper.setTo(map.get("email").toString());
            messageHelper.setFrom("WLR IoT Platform" + '<' + "info@baiblog.top" + '>');

            javaMailSender.send(mimeMessage);

        } catch (Exception e) {
            throw new CustomException(400, "邮件发送失败！请稍后重试或联系管理员。");
        }
    }


}
