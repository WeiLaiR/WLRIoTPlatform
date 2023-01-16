package com.wei.iotplatformuserservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wei.iotplatformuserservice.pojo.Login;

import java.util.Map;

public interface LoginService extends IService<Login> {

    Map<String, Object> signIn(String email, String password);

    Map<String, Object> deleteLoginInfo(Long uid);

    Map<String, Object> register(String email, String password, String vCode);

}
