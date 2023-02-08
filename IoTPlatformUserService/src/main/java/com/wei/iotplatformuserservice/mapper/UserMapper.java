package com.wei.iotplatformuserservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wei.iotplatformuserservice.pojo.User;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<User> {

    List<User> qUserListPQbc(String val, Integer start, Integer limit);

    List<User> qUserListPage(Integer start, Integer limit);

    Integer qUserListCount();

    Integer qUserListCountQbc();
}
