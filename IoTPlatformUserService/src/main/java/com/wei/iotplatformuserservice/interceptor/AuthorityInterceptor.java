package com.wei.iotplatformuserservice.interceptor;

import com.wei.iotplatformuserservice.annotation.AuthorityAnnotation;
import com.wei.iotplatformuserservice.exception.CustomException;
import com.wei.iotplatformuserservice.mapper.LoginMapper;
import com.wei.iotplatformuserservice.utils.TokenUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class AuthorityInterceptor implements HandlerInterceptor {

    private LoginMapper loginMapper;
    @Autowired
    public void setLoginMapper(LoginMapper loginMapper) {
        this.loginMapper = loginMapper;
    }

    @Override
    public boolean preHandle(@NotNull HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull Object handler) {
        //不是映射到方法，直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;

        Method method = handlerMethod.getMethod();
        AuthorityAnnotation annotation = method.getAnnotation(AuthorityAnnotation.class);
        //为空则无需限制，直接放行
        if (annotation == null) {
            return true;
        }

        Long id = TokenUtils.getId();

        Integer status = loginMapper.queryStatus(id);
        if (status == 0) {
            throw new CustomException(401, "您的账号已被禁用！");
        }

        if (annotation.sign() > status) {
            throw new CustomException(400, "您无权访问！");
        }

        return true;
    }
}
