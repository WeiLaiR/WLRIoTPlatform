package com.wei.iotplatformuserservice.controller;

import com.wei.iotplatformuserservice.annotation.AuthorityAnnotation;
import com.wei.iotplatformuserservice.pojo.Login;
import com.wei.iotplatformuserservice.service.LoginService;
import com.wei.iotplatformuserservice.service.UserService;
import com.wei.iotplatformuserservice.service.UserSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user/login")
public class LoginController {

    private LoginService loginService;

    @Autowired
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
    }

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    private UserSettingService userSettingService;
    @Autowired
    public void setUserSettingService(UserSettingService userSettingService) {
        this.userSettingService = userSettingService;
    }

    /**
     * 登录
     * @param login 登录信息
     * @return 状态值
     */
    @PostMapping("/sign_in")
    public Map<String, Object> SignIn(@RequestBody Login login) {
//        System.out.println(login);
        return loginService.signIn(login.getEmail(), login.getPassword());
    }

    @PostMapping("/register")
    public Map<String, Object> register(@RequestBody Map<String, String> lmap) {
        return loginService.register(lmap.get("email"), lmap.get("password"), lmap.get("code"));
    }

    @PostMapping("/log_out")
    public Map<String, Object> logOut() {
        return loginService.logOut();
    }

    @AuthorityAnnotation(sign = 2)
    @GetMapping("/get_status_zero_list")
    public Map<String, Object> getStatusZeroList() {
        return loginService.queryStatusZeroList();
    }

    @AuthorityAnnotation(sign = 2)
    @PostMapping("/user_pass")
    public Map<String, Object> userPass(@RequestBody Map<String, Object> map) {
        Long uid = (Long) map.get("uid");
        loginService.updateStatus(uid);
        userSettingService.createUserSetting(uid);
        return userService.insertUser(uid, (String) map.get("email"));
    }

    @AuthorityAnnotation(sign = 2)
    @PostMapping("/user_reject")
    public Map<String, Object> userReject(@RequestBody Long uid) {
        return loginService.updateStatus(uid);
    }

    @AuthorityAnnotation(sign = 2)
    @GetMapping("/get_status/{uid}")
    public Map<String, Object> getStatus(@PathVariable Long uid) {
        return loginService.queryStatus(uid);
    }

    @PostMapping("/verification")
    public Map<String, Object> verification(@RequestBody String password) {
        return loginService.verification(password);
    }
}


