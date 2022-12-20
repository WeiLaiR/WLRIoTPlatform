package com.wei.devicegateway.interceptor;

import com.wei.devicegateway.exception.CustomException;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class HttpDeviceInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //不是映射到方法，直接通过
        if (!(handler instanceof HandlerMethod)){
            return true;
        }

        //获取token
        String token = request.getHeader("token");

        if (StringUtils.hasText(token)) {
            throw new CustomException("Error", "The token is empty!");
        }

        return true;
    }
}
