package com.wei.iotplatformuserservice.controller;

import com.wei.iotplatformuserservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user/user")
public class UserController {
    private UserService userService;
    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/userinfo/{uid}")
    public Map<String, Object> getUserInfo(@PathVariable Long uid) {
        return userService.searchUserInfo(uid);
    }

}
