package com.wei.iotplatformuserservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wei.iotplatformuserservice.pojo.Login;

import java.util.Map;

public interface LoginService extends IService<Login> {

    /**
     * 登录
     * @param email 邮箱
     * @param password 密码
     * @return 状态值及token
     */
    Map<String, Object> signIn(String email, String password);

    Map<String, Object> logOut();

    Map<String, Object> deleteLoginInfo(Long uid);

    Map<String, Object> register(String email, String password, String vCode);

    Map<String, Object> queryStatusZeroList();

    Map<String, Object> updateStatus(Long uid);

    Map<String, Object> queryStatus(Long uid);

    Map<String, Object> verification(String password);

}
