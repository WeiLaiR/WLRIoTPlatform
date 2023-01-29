package com.wei.iotplatformuserservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wei.iotplatformuserservice.pojo.Login;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginMapper extends BaseMapper<Login> {

    /**
     * 登陆查询
     * @param email 邮箱
     * @return 登录信息
     */
    Login queryLogin(String email);

    /**
     * 查询邮箱是否存在
     * @param email 邮箱
     * @return 邮箱
     */
    String queryEmailEmpty(String email);
}
