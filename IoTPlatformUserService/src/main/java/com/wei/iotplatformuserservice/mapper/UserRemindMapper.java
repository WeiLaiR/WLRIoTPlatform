package com.wei.iotplatformuserservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wei.iotplatformuserservice.pojo.UserRemind;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRemindMapper extends BaseMapper<UserRemind> {
    Integer queryRemindUnreadCount(Long uid);

    List<UserRemind> queryRemindUnreadList(Long uid);

    List<UserRemind> queryRemindListPage(Long uid, Integer start, Integer size);

    Integer queryRemindPageCount();
}
