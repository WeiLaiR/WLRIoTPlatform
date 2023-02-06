package com.wei.mailservice.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BuildVCode {

    //    设置token过期时间，单位为秒，当前为五分钟
    private static final long TIMEOUT = 300;

    //    验证码可再次请求时间
    private static final long TRYAGAIN = 120;


    private RedisUtil redisUtil;

    @Autowired
    public void setRedisUtil(RedisUtil redisUtil) {
        this.redisUtil = redisUtil;
    }


    /**
     * 生成验证码,并存入Redis
     *
     * @return 验证码
     */
    public String buildVCode(String eMail) {
        String randomCode = Integer.toString((int) ((Math.random() * 9 + 1) * 100000));

        redisUtil.set(eMail.concat("vCode"), randomCode, TIMEOUT);
        redisUtil.set(eMail.concat("0ta"), "0", TRYAGAIN);

        return randomCode;
    }
}
