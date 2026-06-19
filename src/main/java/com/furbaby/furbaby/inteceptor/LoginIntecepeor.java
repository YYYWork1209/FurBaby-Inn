package com.furbaby.furbaby.inteceptor;

import com.furbaby.furbaby.entity.Result;
import com.furbaby.furbaby.exception.UnAuthorizedException;
import com.furbaby.furbaby.utils.JWTUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

/**
 * 登录拦截器
 * 用于检查用户是否登录,如果未登录则跳转到登录页
 */
@Component
public class LoginIntecepeor implements HandlerInterceptor {

    @Autowired
    private JWTUtils jwtUtils;

    /**
     * 拦截请求,检查用户是否登录
     * 如果用户未登录,则跳转到登录页(返回状态码，让前端进行跳转)
     * 如果用户已登录,则放行请求
     * @param request 请求对象
     * @param response 响应对象
     * @param handler 处理器对象
     * @return true 放行请求, false 拦截请求
     * @throws Exception 异常对象
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        return true;
    }


    /**
     * 处理请求完成后,执行
     * 顺序在 postHandle之后
     * @param request 请求对象
     * @param response 响应对象
     * @param handler 处理器对象
     * @param ex 异常对象
     * @throws Exception 异常对象
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }


    /**
     * 处理请求完成后,执行顺序在 afterCompletion之前
     * @param request 请求对象
     * @param response 响应对象
     * @param handler 处理器对象
     * @param modelAndView 模型视图对象,用于封装响应数据
     * @throws Exception 异常对象
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }
}
