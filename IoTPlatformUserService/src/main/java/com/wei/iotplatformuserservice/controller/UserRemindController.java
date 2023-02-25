package com.wei.iotplatformuserservice.controller;

import com.wei.iotplatformuserservice.pojo.UserRemind;
import com.wei.iotplatformuserservice.service.UserRemindService;
import com.wei.iotplatformuserservice.service.UserService;
import com.wei.iotplatformuserservice.service.UserSettingService;
import com.wei.iotplatformuserservice.utils.RedisUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class UserRemindController {

    private UserRemindService userRemindService;
    @Autowired
    public void setUserRemindService(UserRemindService userRemindService) {
        this.userRemindService = userRemindService;
    }

    private UserSettingService userSettingService;
    @Autowired
    public void setUserSettingService(UserSettingService userSettingService) {
        this.userSettingService = userSettingService;
    }

    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private RedisUtil redisUtil;
    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }

    private RabbitTemplate rabbitTemplate;

    @Autowired
    private void setRabbitTemplate(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @PutMapping("/insertRemind")
    public void insertRemind(@RequestBody Map<String, Object> map) {
        UserRemind remind = new UserRemind();
        remind.setUid((Long) map.get("uid"));
        remind.setMessage((String) map.get("message"));
        userRemindService.insertUserRemind(remind);
        if (userSettingService.querySendEmail((Long) map.get("uid"))) {
            if (redisUtil.get("em" + map.get("did")) == null) {
                String email = userService.getById((Long) map.get("uid")).getEmail();
                map.put("email", email);
                redisUtil.set("em" + map.get("did"), "Y", 1200);
                rabbitTemplate.convertAndSend("mail.direct", "send", map);
            }
        }
    }

    @GetMapping("/user/getCount")
    public Map<String, Object> getCount() {
        return userRemindService.queryRemindUnreadCount();
    }

    @GetMapping("/user/getRemindListPage/{start}/{limit}")
    public Map<String, Object> getRemindListPage(@PathVariable Integer start, @PathVariable Integer limit) {
        return userRemindService.queryRemindListPage((start - 1) * limit, limit);
    }

    @GetMapping("/user/getRemindUnreadList")
    public Map<String, Object> getRemindUnreadList() {
        return userRemindService.queryRemindUnreadList();
    }

    @PostMapping("/user/isRead")
    public void isRead(@RequestBody Long rid) {
        userRemindService.isRead(rid);
    }
}
