package com.example.usermanagement.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTPayload;
import cn.hutool.jwt.JWTUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * JWT工具类
 *
 * @author example
 * @since 1.0.0
 */
@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expire-time}")
    private long expireTime;

    /**
     * 生成Token
     *
     * @param userId   用户ID
     * @param username 用户名
     * @param role     角色
     * @return Token字符串
     */
    public String generateToken(Long userId, String username, String role) {
        Map<String, Object> payload = new HashMap<>();
        payload.put(JWTPayload.ISSUED_AT, new Date()); // 签发时间
        payload.put(JWTPayload.EXPIRES_AT, DateUtil.offsetMillisecond(new Date(), (int) expireTime)); // 过期时间
        payload.put(JWTPayload.NOT_BEFORE, new Date()); // 生效时间
        payload.put("userId", userId); // 用户ID
        payload.put("username", username); // 用户名
        payload.put("role", role); // 角色

        return JWTUtil.createToken(payload, secret.getBytes());
    }

    /**
     * 验证Token
     *
     * @param token Token字符串
     * @return 是否有效
     */
    public boolean validateToken(String token) {
        try {
            JWT jwt = JWTUtil.parseToken(token).setKey(secret.getBytes());
            return jwt.verify();
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 从Token中获取用户ID
     *
     * @param token Token字符串
     * @return 用户ID
     */
    public Long getUserIdFromToken(String token) {
        JWT jwt = JWTUtil.parseToken(token).setKey(secret.getBytes());
        return Long.parseLong(jwt.getPayload("userId").toString());
    }

    /**
     * 从Token中获取用户名
     *
     * @param token Token字符串
     * @return 用户名
     */
    public String getUsernameFromToken(String token) {
        JWT jwt = JWTUtil.parseToken(token).setKey(secret.getBytes());
        return jwt.getPayload("username").toString();
    }

    /**
     * 从Token中获取角色
     *
     * @param token Token字符串
     * @return 角色
     */
    public String getRoleFromToken(String token) {
        JWT jwt = JWTUtil.parseToken(token).setKey(secret.getBytes());
        return jwt.getPayload("role").toString();
    }
}