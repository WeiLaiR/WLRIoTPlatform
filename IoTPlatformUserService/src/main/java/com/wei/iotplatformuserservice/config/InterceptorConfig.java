package com.wei.iotplatformuserservice.config;

import com.wei.iotplatformuserservice.interceptor.AuthorityInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authorityInterceptor())
                //拦截
                .addPathPatterns("/**")
                //放行
                .excludePathPatterns("/user/login/sign_in", "/user/login/register", "/user/ras/getPublicKey")
                //优先级，越小越高
                .order(0);
    }

    @Bean
    public AuthorityInterceptor authorityInterceptor() {
        return new AuthorityInterceptor();
    }
}
