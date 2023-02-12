package com.wei.iotplatformuserservice;

import com.wei.iotplatformuserservice.mapper.LoginMapper;
import com.wei.iotplatformuserservice.mapper.UserMapper;
import com.wei.iotplatformuserservice.pojo.Login;
import com.wei.iotplatformuserservice.pojo.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class IoTPlatformUserServiceApplicationTests {

    @Autowired
    private LoginMapper loginMapper;


    @Autowired
    private UserMapper userMapper;

    @Test
    void contextLoads() {




    }

    @Test
    void Test1() {
        System.out.println(loginMapper.queryLogin("1@a.com"));
    }

    @Test
    void Test2() {
        for (int i = 0; i < 9999; i++) {
            int num = 667 + i;
            User user = new User();
            user.setUid((long) num);
            user.setAge((short) 23);
            user.setEmail(num + "@a.com");
            user.setSex(true);
            user.setPhoneNumber(num + "phone");
            user.setRemarks(num + "remarks");
            user.setUname(num + "uname");
            user.setBriefIntroduction(num + "briefIntroduction");
            userMapper.insert(user);
        }
    }

    @Test
    void Test3() {
        System.out.println(userMapper.qUserListPQbc("%", 0, 10));
    }

    @Test
    void Test4() {
        System.out.println(userMapper.qUserListPage( 0, 10));
    }

    @Test
    void Test5() {
        for (int i = 0; i < 100; i++) {
            Login login = new Login();
            login.setEmail(i + "@ccc.com");
            login.setPassword("123456");
            loginMapper.insert(login);
        }
    }
}
