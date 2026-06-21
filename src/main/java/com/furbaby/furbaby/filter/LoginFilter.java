package com.furbaby.furbaby.filter;

import com.furbaby.furbaby.exception.UnAuthorizedException;
import com.furbaby.furbaby.security.TokenBlacklist;
import com.furbaby.furbaby.utils.JWTUtils;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;

/**
 * 全局 Token 校验过滤器。
 *
 * 策略：不强制所有请求携带 Token（公开接口无需认证），
 * 但如果请求携带了 Authorization 头，则校验其合法性和黑名单状态。
 */
@WebFilter("/*")
public class LoginFilter implements Filter {

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private TokenBlacklist tokenBlacklist;

    @Override
    public void init(jakarta.servlet.FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String token = request.getHeader("Authorization");

        // 无 Token → 公开接口，直接放行
        if (token == null || token.isBlank()) {
            filterChain.doFilter(request, response);
            return;
        }

        // 有 Token → 校验格式
        if (!token.startsWith("Bearer ")) {
            throw new UnAuthorizedException("Token格式错误，请以 Bearer 开头");
        }
        token = token.substring(7);

        // 校验签名和过期时间
        if (!jwtUtils.validateToken(token)) {
            throw new UnAuthorizedException("Token无效或已过期，请重新登录");
        }

        // 校验黑名单
        if (tokenBlacklist.isBlacklisted(token)) {
            throw new UnAuthorizedException("Token已失效，请重新登录");
        }

        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
