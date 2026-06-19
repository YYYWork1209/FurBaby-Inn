package com.furbaby.furbaby.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 关闭默认的表单登录和 HTTP Basic
            .formLogin(form -> form.disable())
            .httpBasic(basic -> basic.disable())
            // 放行所有请求（权限控制暂时交给你的 JWT 过滤器）
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            )
            // 禁用 CSRF 以便 JWT 过滤器正常工作（通常 JWT 不需要 CSRF）
            .csrf(csrf -> csrf.disable());

        return http.build();
    }
}