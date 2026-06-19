package com.furbaby.furbaby.filter;

import com.furbaby.furbaby.exception.UnAuthorizedException;
import com.furbaby.furbaby.utils.JWTUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * 登录过滤器
 * 用于检查用户是否登录
 * 拦截所有请求,检查用户是否登录
 */
@WebFilter("/*")
public class LoginFilter implements Filter {

    @Autowired
    private JWTUtils jwtUtils;

    /**
     * 初始化过滤器,在应用启动时调用只执行一次
     * @param filterConfig 过滤器配置
     * @throws ServletException 初始化过滤器时抛出的异常
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        // 转换为HttpServletRequest，不然无法获取请求头中的token
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 获取请求URI,依据请求路径判断是否需要过滤
        String uri = request.getRequestURI();
        // 若是登录和注册请求则放行请求
        if(uri.contains("/user/login") || uri.contains("/user/register")){
            filterChain.doFilter(request, response);
        }

        // 进行token验证
        // 从请求头中获取token
        // 如果token为空,则抛出未授权异常
        String token =  request.getHeader("Authorization");
        // 如果token以"Bearer "开头,则移除"Bearer "前缀,保留token内容
        if (token.startsWith("Bearer ")) {
            token = token.substring(7);
        }else{
            throw new UnAuthorizedException("Token格式错误,请以Bearer \"开头");
        }

        // 如果token不为空,则进行Token验证
        if(!jwtUtils.validateToken(token)){
            //解析失败,则抛出未授权异常
            throw new UnAuthorizedException("请重新登录！");
        }
        // 如果token验证通过,则放行请求
         filterChain.doFilter(request, response);
    }

    /**
     * 销毁过滤器,在应用关闭时调用只执行一次
     */
    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
