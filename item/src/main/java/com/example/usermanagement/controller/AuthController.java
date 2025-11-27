package com.example.usermanagement.controller;

import com.example.usermanagement.common.Result;
import com.example.usermanagement.entity.User;
import com.example.usermanagement.service.UserService;
import com.example.usermanagement.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 认证控制器
 *
 * @author example
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/auth")
@Tag(name = "认证接口", description = "用户注册、登录、注销接口")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtil jwtUtil;

    /**
     * 用户注册
     *
     * @param registerRequest 注册请求
     * @return 注册结果
     */
    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "用户注册接口")
    public Result<Map<String, Object>> register(
            @Parameter(description = "注册请求") @RequestBody RegisterRequest registerRequest) {
        try {
            User user = userService.register(
                    registerRequest.getUsername(),
                    registerRequest.getEmail(),
                    registerRequest.getPassword()
            );

            Map<String, Object> data = new HashMap<>();
            data.put("userId", user.getId());
            data.put("username", user.getUsername());
            data.put("email", user.getEmail());

            return Result.success(data, "注册成功");
        } catch (RuntimeException e) {
            return Result.failed(e.getMessage());
        }
    }

    /**
     * 用户登录
     *
     * @param loginRequest 登录请求
     * @return 登录结果
     */
    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户登录接口")
    public Result<Map<String, Object>> login(
            @Parameter(description = "登录请求") @RequestBody LoginRequest loginRequest) {
        try {
            User user = userService.login(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
            );

            // 生成Token
            String token = jwtUtil.generateToken(user.getId(), user.getUsername(), user.getRole());

            Map<String, Object> data = new HashMap<>();
            data.put("token", token);
            data.put("userId", user.getId());
            data.put("username", user.getUsername());
            data.put("role", user.getRole());

            return Result.success(data, "登录成功");
        } catch (RuntimeException e) {
            return Result.failed(e.getMessage());
        }
    }

    /**
     * 用户注销
     *
     * @return 注销结果
     */
    @PostMapping("/logout")
    @Operation(summary = "用户注销", description = "用户注销接口")
    public Result<String> logout() {
        // 服务端简化处理，客户端删除本地Token即可
        return Result.success(null, "注销成功");
    }

    /**
     * 注册请求参数
     */
    public static class RegisterRequest {
        private String username;
        private String email;
        private String password;

        // Getters and Setters
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    /**
     * 登录请求参数
     */
    public static class LoginRequest {
        private String username;
        private String password;

        // Getters and Setters
        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }
}