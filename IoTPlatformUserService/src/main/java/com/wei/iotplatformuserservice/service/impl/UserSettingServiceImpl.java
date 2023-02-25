package com.wei.iotplatformuserservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wei.iotplatformuserservice.exception.CustomException;
import com.wei.iotplatformuserservice.mapper.UserSettingMapper;
import com.wei.iotplatformuserservice.pojo.UserSetting;
import com.wei.iotplatformuserservice.service.UserSettingService;
import com.wei.iotplatformuserservice.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserSettingServiceImpl extends ServiceImpl<UserSettingMapper, UserSetting> implements UserSettingService {

    private UserSettingMapper userSettingMapper;
    @Autowired
    public void setUserSettingMapper(UserSettingMapper userSettingMapper) {
        this.userSettingMapper = userSettingMapper;
    }

    @Override
    public Map<String, Object> selectUserSetting() {
        Map<String, Object> map = new HashMap<>();
        Long id = TokenUtils.getId();
        try {
            UserSetting userSetting = userSettingMapper.selectById(id);
            if (userSetting != null) {
                map.put("status", 200);
                map.put("message", "查询成功");
                map.put("data", userSetting);
            }else {
                map.put("status", 400);
                map.put("message", "查询失败");
            }

        }catch (Exception e){
            throw new CustomException(400, "出现了未知异常！(UserSettingService.selectUserSetting)");
        }
        return map;
    }

    @Override
    public void createUserSetting(Long uid) {
        UserSetting userSetting = new UserSetting();
        userSetting.setUid(uid);
        userSettingMapper.insert(userSetting);
    }

    @Override
    public Map<String, Object> updateUserSetting(UserSetting userSetting) {
        Map<String, Object> map = new HashMap<>();
        Long id = TokenUtils.getId();
        userSetting.setUid(id);
        try {
            if (userSettingMapper.updateById(userSetting) > 0) {
                map.put("status", 200);
                map.put("message", "更新成功");
            } else {
                map.put("status", 400);
                map.put("message", "更新失败");
            }
        }catch (Exception e){
            throw new CustomException(400, "出现了未知异常！(UserSettingService.updateUserSetting)");
        }
        return map;
    }

    @Override
    public Boolean querySendEmail(Long uid) {
        return uid == null ? userSettingMapper.selectById(TokenUtils.getId()).getSendEmail() : userSettingMapper.selectById(uid).getSendEmail();
    }
}
