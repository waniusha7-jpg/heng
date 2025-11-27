package com.example.usermanagement.interceptor;

import cn.hutool.core.util.StrUtil;
import com.example.usermanagement.util.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 权限拦截器
 *
 * @author example
 * @since 1.0.0
 */
@Component
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头中获取Token
        String authorization = request.getHeader("Authorization");
        if (StrUtil.isEmpty(authorization) || !authorization.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Missing or invalid token");
            return false;
        }

        // 提取Token
        String token = authorization.substring(7);

        // 验证Token
        if (!jwtUtil.validateToken(token)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid or expired token");
            return false;
        }

        // 检查权限
        String requestURI = request.getRequestURI();
        String role = jwtUtil.getRoleFromToken(token);

        // 管理员可以访问所有接口
        if ("ADMIN".equals(role)) {
            return true;
        }

        // 普通用户只能访问 /api/user/** 接口
        if (requestURI.startsWith("/api/user/")) {
            return true;
        }

        // 没有权限
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getWriter().write("Access denied");
        return false;
    }
}