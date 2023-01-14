package com.wei.iotplatformuserservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wei.iotplatformuserservice.mapper.LoginMapper;
import com.wei.iotplatformuserservice.pojo.Login;
import com.wei.iotplatformuserservice.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class LoginServiceImpl extends ServiceImpl<LoginMapper, Login> implements LoginService {
    private LoginMapper loginMapper;
    @Autowired
    public void setLoginMapper(LoginMapper loginMapper) {
        this.loginMapper = loginMapper;
    }

    @Override
    public Map<String, Object> signIn(String email, String password) {
        Map<String, Object> map = new HashMap<>();
        QueryWrapper<Login> wrapper = new QueryWrapper<>();
        wrapper.eq("email", email);
        Login login = loginMapper.selectOne(wrapper);
        if (login != null) {
            if (login.getPassword().equals(password)) {
                map.put("status", 200);
                map.put("message", "登录成功");
            } else {
                map.put("status", 400);
                map.put("message", "密码错误");
            }
        } else {
            map.put("status", 400);
            map.put("message", "用户不存在");
        }

        return map;
    }

    @Override
    public Map<String, Object> deleteLoginInfo(Long uid) {
        Map<String, Object> map = new HashMap<>();
        if (loginMapper.deleteById(uid) > 0) {
            map.put("status", 200);
            map.put("message", "删除成功");
        }else {
            map.put("status", 400);
            map.put("message", "删除失败");
        }
        return map;
    }
}
