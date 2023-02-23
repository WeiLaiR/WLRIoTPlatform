package com.wei.iotplatformuserservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wei.iotplatformuserservice.pojo.UserRemind;

import java.util.Map;

public interface UserRemindService extends IService<UserRemind> {

    void insertUserRemind(UserRemind userRemind);

    Map<String, Object> queryRemindUnreadCount();

    Map<String, Object> queryRemindListPage(Integer start, Integer size);

    Map<String, Object> queryRemindUnreadList();

    void isRead(Long rid);
}
