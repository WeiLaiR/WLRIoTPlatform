package com.wei.iotplatformuserservice.controller;

import com.wei.iotplatformuserservice.pojo.UserRemind;
import com.wei.iotplatformuserservice.service.UserRemindService;
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

    @PutMapping("/insertRemind")
    public void insertRemind(@RequestBody Map<String, Object> map) {
        UserRemind remind = new UserRemind();
        remind.setUid((Long) map.get("uid"));
        remind.setMessage((String) map.get("message"));
        userRemindService.insertUserRemind(remind);
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
}
