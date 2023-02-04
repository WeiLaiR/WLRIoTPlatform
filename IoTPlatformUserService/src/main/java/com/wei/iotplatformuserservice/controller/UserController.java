package com.wei.iotplatformuserservice.controller;

import com.wei.iotplatformuserservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user/users")
public class UserController {
    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    /**
     * 获取用户信息
     * @param uid 用户id
     * @return 用户信息
     */
    @GetMapping("/userinfo/{uid}")
    public Map<String, Object> getUserInfo(@PathVariable Long uid) {
        return userService.searchUserInfo(uid);
    }

}
