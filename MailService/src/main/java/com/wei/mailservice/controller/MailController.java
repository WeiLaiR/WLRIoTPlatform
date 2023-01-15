package com.wei.mailservice.controller;

import com.wei.mailservice.exception.CustomException;
import com.wei.mailservice.utils.BuildMail;
import com.wei.mailservice.utils.BuildVCode;
import com.wei.mailservice.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.internet.MimeMessage;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mail")
public class MailController {


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

    private RedisUtil redisUtil;

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    @PostMapping("/send")
    public Map<String, Object> sendMail(@RequestBody String eMail) {
//        System.out.println(eMail);
        if (redisUtil.get(eMail + "0ta") != null) {
            throw new CustomException("400", "您的请求过于频繁，请稍后再试！");
        }

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        String vCode = buildVCode.buildVCode(eMail);
        Map<String, Object> map = new HashMap<>();

        try {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);

            messageHelper.setSubject("[验证码]——WLR IoT Platform");
            messageHelper.setText(BuildMail.buildContent(vCode), true);
            messageHelper.setTo(eMail);
            messageHelper.setFrom("WLR IoT Platform" + '<' + "info@baiblog.top" + '>');

            javaMailSender.send(mimeMessage);

        } catch (Exception e) {
            throw new CustomException("400", "邮件发送失败！轻稍后重试或联系管理员。");
        }

        map.put("status", "200");
        map.put("message", "邮件发送成功！");

        return map;
    }
}
