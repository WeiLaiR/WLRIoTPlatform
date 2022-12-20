package com.wei.devicegateway.config;

import com.wei.devicegateway.interceptor.HttpDeviceInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {


    private HttpDeviceInterceptor httpDeviceInterceptor;

    @Autowired
    private void setHttpDeviceInterceptor(HttpDeviceInterceptor httpDeviceInterceptor) {
        this.httpDeviceInterceptor = httpDeviceInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(httpDeviceInterceptor)
//                拦截所有请求查看token是否合法
                .addPathPatterns("/**")
//                设置优先级（越小越高）
                .order(0);
    }
}
