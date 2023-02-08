package com.wei.iotplatformuserservice.controller;

import com.wei.iotplatformuserservice.pojo.Login;
import com.wei.iotplatformuserservice.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/user/login")
public class LoginController {

    private LoginService loginService;

    @Autowired
    public void setLoginService(LoginService loginService) {
        this.loginService = loginService;
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
}
