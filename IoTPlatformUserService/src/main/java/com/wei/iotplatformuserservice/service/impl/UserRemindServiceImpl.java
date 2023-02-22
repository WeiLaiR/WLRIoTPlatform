package com.wei.iotplatformuserservice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wei.iotplatformuserservice.exception.CustomException;
import com.wei.iotplatformuserservice.mapper.UserRemindMapper;
import com.wei.iotplatformuserservice.pojo.UserRemind;
import com.wei.iotplatformuserservice.service.UserRemindService;
import com.wei.iotplatformuserservice.utils.TokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserRemindServiceImpl extends ServiceImpl<UserRemindMapper, UserRemind> implements UserRemindService {

    private UserRemindMapper userRemindMapper;
    @Autowired
    public void setUserRemindMapper(UserRemindMapper userRemindMapper) {
        this.userRemindMapper = userRemindMapper;
    }

    @Override
    public void insertUserRemind(UserRemind userRemind) {
        userRemindMapper.insert(userRemind);
    }

    @Override
    public Map<String, Object> queryRemindUnreadCount() {
        Long uid = TokenUtils.getId();
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("data", userRemindMapper.queryRemindUnreadCount(uid));
            map.put("status", 200);
            map.put("message", "查询成功！");
        } catch (Exception e) {
            throw new CustomException(400, "出现未知异常！(UserRemindServiceImpl.queryRemindUnreadCount)");
        }
        return map;
    }

    @Override
    public Map<String, Object> queryRemindListPage(Integer start, Integer size) {
        Long uid = TokenUtils.getId();
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("data", userRemindMapper.queryRemindListPage(uid, start, size));
            map.put("total", userRemindMapper.queryRemindPageCount());
            map.put("status", 200);
            map.put("message", "查询成功！");
        } catch (Exception e) {
            throw new CustomException(400, "出现未知异常！(UserRemindServiceImpl.queryRemindListPage)");
        }
        return map;
    }

    @Override
    public Map<String, Object> queryRemindUnreadList() {
        Long uid = TokenUtils.getId();
        Map<String, Object> map = new HashMap<>();
        try {
            map.put("data", userRemindMapper.queryRemindUnreadList(uid));
            map.put("status", 200);
            map.put("message", "查询成功！");
        } catch (Exception e) {
            throw new CustomException(400, "出现未知异常！(UserRemindServiceImpl.queryRemindUnreadList)");
        }
        return map;
    }
}
