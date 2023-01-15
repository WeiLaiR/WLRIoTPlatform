package com.wei.iotplatformuserservice.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Objects;

public class TokenUtils {

    /**
     * 用于生成token
     *
     * @param eid 也就是用户id
     * @param str 用于加签的部分
     * @return 生成好的token
     */
    public static String getToken(Long eid, String str) {
//        id作为载荷
        return JWT.create().withAudience(eid.toString())
//                设置发布时间，因为时间是再变化的，这样生成的token每一次都不相同，有利于防止伪造token
                .withIssuedAt(new Date())
//                设置有效时间,也就是token的过期时间,当前过期时间为获取token的72小时后 (目前使用redis设置失效时间)
//                .withExpiresAt(DateUtil.offsetHour(new Date(),72))
//                sign签名部分通过HMAC-SHA256加密,可用于验证token是否合法
//                这里输入的数据就相当于加盐，内容非常重要，不可泄漏
                .sign(Algorithm.HMAC256(DigestUtils.sha3_256Hex(str.concat("JiaYan"))));
    }

    /**
     * 通过token获取用户id
     *
     * @return 用户id
     */
    public static Long getId() {
        try {
            HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
            String token = request.getHeader("token");
            String s = JWT.decode(token).getAudience().get(0);
            return Long.valueOf(s);
        } catch (Exception e) {
            return null;
        }
    }

}
