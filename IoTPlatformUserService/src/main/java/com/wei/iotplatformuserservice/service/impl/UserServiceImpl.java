package com.wei.iotplatformuserservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wei.iotplatformuserservice.mapper.UserMapper;
import com.wei.iotplatformuserservice.pojo.User;
import com.wei.iotplatformuserservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    private UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper) {
        this.userMapper = userMapper;
    }


    @Override
    public Map<String, Object> searchUserInfo(Long uid) {
        Map<String, Object> map = new HashMap<>();
        User user = userMapper.selectById(uid);
        if (user != null) {
            map.put("status", 200);
            map.put("message", "查询成功");
            map.put("data", user);
        } else {
            map.put("status", 400);
            map.put("message", "用户不存在");
        }

        return map;
    }

    @Override
    public Map<String, Object> deleteUserInfo(Long uid) {
        Map<String, Object> map = new HashMap<>();
        if (userMapper.deleteById(uid) > 0) {
            map.put("status", 200);
            map.put("message", "删除成功");
        } else {
            map.put("status", 400);
            map.put("message", "删除失败");
        }
        return map;
    }
}
