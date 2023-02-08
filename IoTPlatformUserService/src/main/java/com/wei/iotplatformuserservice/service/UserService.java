package com.wei.iotplatformuserservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wei.iotplatformuserservice.pojo.User;

import java.util.Map;

public interface UserService extends IService<User> {
    /**
     * 查询用户信息
     * @param uid 用户id
     * @return 用户信息及状态
     */
    Map<String, Object> searchUserInfo(Long uid);

    Map<String, Object> deleteUserInfo(Long uid);

    Map<String, Object> updateUserInfo(User user);

    Map<String, Object> queryUserListP(Integer start, Integer limit, String val);
}
