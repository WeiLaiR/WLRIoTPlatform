package com.wei.iotplatformuserservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wei.iotplatformuserservice.pojo.User;

import java.util.Map;

public interface UserService extends IService<User> {
    Map<String, Object> searchUserInfo(Long uid);

    Map<String, Object> deleteUserInfo(Long uid);
}
