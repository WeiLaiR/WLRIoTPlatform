package com.wei.iotplatformuserservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wei.iotplatformuserservice.pojo.UserSetting;

import java.util.Map;

public interface UserSettingService extends IService<UserSetting> {

    Map<String, Object> selectUserSetting();

    void createUserSetting(Long uid);

    Map<String, Object> updateUserSetting(UserSetting userSetting);

    Boolean querySendEmail(Long uid);


}
