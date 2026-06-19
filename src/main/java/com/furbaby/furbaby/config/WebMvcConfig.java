package com.furbaby.furbaby.config;

import com.furbaby.furbaby.inteceptor.LoginIntecepeor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Autowired
    private LoginIntecepeor loginIntecepeor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginIntecepeor)
                .addPathPatterns("/**")  // 所有路径
                .excludePathPatterns("/user/login","/user/register");  // 排除登录和注册路径
    }
}
