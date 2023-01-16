package com.wei.iotplatformuserservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wei.iotplatformuserservice.pojo.Login;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginMapper extends BaseMapper<Login> {
    Login queryLogin(String email);


    String queryEmailEmpty(String email);
}
