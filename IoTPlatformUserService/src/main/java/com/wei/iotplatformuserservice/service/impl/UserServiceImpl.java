package com.wei.iotplatformuserservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wei.iotplatformuserservice.exception.CustomException;
import com.wei.iotplatformuserservice.mapper.UserMapper;
import com.wei.iotplatformuserservice.pojo.User;
import com.wei.iotplatformuserservice.service.UserService;
import com.wei.iotplatformuserservice.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
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
        if (uid == null) {
            uid = TokenUtils.getId();
            if (uid == null) {
                throw new CustomException(400, "出现了未知错误，无法获取用户信息！");
            }
        }
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

    @Override
    public Map<String, Object> updateUserInfo(User user) {
        Map<String, Object> map = new HashMap<>();
        if (user.getUid() == null) {
            user.setUid(TokenUtils.getId());
        }
        try {
            if (userMapper.updateById(user) > 0) {
                map.put("status", 200);
                map.put("message", "信息保存成功！");
            } else {
                map.put("status", 400);
                map.put("message", "数据更新失败，请稍后再试！");
            }
        } catch (Exception e) {
            throw new CustomException(400, "出现未知异常，请稍后再试！");
        }
        return map;
    }

    @Override
    public Map<String, Object> queryUserListP(Integer start, Integer limit, String val) {
        Map<String, Object> map = new HashMap<>();
        List<User> users;
        int count;

        try{
            if (val.equals("null")) {
                users = userMapper.qUserListPage(start, limit);
                count = userMapper.qUserListCount();
            }else {
                val = val.concat("%");
                users = userMapper.qUserListPQbc(val, start, limit);
                count = userMapper.qUserListCountQbc();
            }
        } catch (Exception e) {
            throw new CustomException(400, "出现了未知错误，无法获取用户信息！");
        }

        map.put("status", 200);
        map.put("message", "查询成功");
        map.put("data", users);
        map.put("total", count);

        return map;
    }

    @Override
    public Map<String, Object> insertUser(Long uid, String Email) {
        HashMap<String, Object> map = new HashMap<>();
        User user = new User();
        String name = Email;
        if (Email.length() > 20) {
            name = Email.substring(0, 15);
        }
        user.setUid(uid);
        user.setEmail(Email);
        user.setUname(name);
        user.setPhoneNumber(uid.toString());
        if (userMapper.insert(user) > 0) {
            map.put("status", 200);
            map.put("message", "用户注册通过！");
        } else {
            map.put("status", 400);
            map.put("message", "出现了未知异常！");
        }
        return map;
    }
}
