package com.wei.mailservice.controller;

import com.wei.mailservice.exception.CustomException;
import com.wei.mailservice.utils.RedisUtil;
import com.wei.mailservice.utils.SendEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/mail/email")
public class MailController {

    private RedisUtil redisUtil;

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

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


    @PostMapping("/send")
    public Map<String, Object> sendMail(@RequestBody Map<String, String> emap) {

//        System.out.println(eMail);
        String email = emap.get("email");
        if (redisUtil.get(email + "0ta") != null) {
            throw new CustomException(400, "您的请求过于频繁，请稍后再试！");
        }

        if(!email.contains("@") || !email.contains(".")){
            throw new CustomException(400, "邮箱格式不正确！");
        }else {
            if (email.indexOf(".") - email.indexOf("@") < 2 || email.indexOf(".") == email.length() - 1 || email.indexOf("@") == 0 ){
                throw new CustomException(400, "邮箱格式不正确！");
            }
        }

        customThreadPool1.execute(() -> {
            sendEmail.senEmailUt(email);
        });

        Map<String, Object> map = new HashMap<>();
        map.put("status", 200);
        map.put("message", "邮件发送成功！");
        return map;
    }
}
