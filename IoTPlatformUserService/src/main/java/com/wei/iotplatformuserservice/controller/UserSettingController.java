package com.wei.iotplatformuserservice.controller;

import com.wei.iotplatformuserservice.exception.CustomException;
import com.wei.iotplatformuserservice.pojo.UserSetting;
import com.wei.iotplatformuserservice.service.UserSettingService;
import com.wei.iotplatformuserservice.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/user/setting")
public class UserSettingController {

    private UserSettingService userSettingService;
    @Autowired
    public void setUserSettingService(UserSettingService userSettingService) {
        this.userSettingService = userSettingService;
    }

    @GetMapping("/query")
    public Map<String, Object> queryUserSetting(){
        return userSettingService.selectUserSetting();
    }

    @PostMapping("/updateSendEmail")
    public Map<String, Object> updateSendEmail(){
        UserSetting userSetting = new UserSetting();
        userSetting.setSendEmail(!userSettingService.getById(TokenUtils.getId()).getSendEmail());

        return userSettingService.updateUserSetting(userSetting);
    }

    @PostMapping("/updateKeepAlive")
    public Map<String, Object> updateKeepAlive(@RequestBody Integer keepAlive){
        if (keepAlive <= 0 || keepAlive > 30) {
            throw new CustomException(400, "Token存活时间应为1-30天，请正确填写！");
        }
        UserSetting userSetting = new UserSetting();
        userSetting.setKeepAlive(keepAlive);
        return userSettingService.updateUserSetting(userSetting);
    }


}
