package com.example.usermanagement.controller;

import com.example.usermanagement.common.Result;
import com.example.usermanagement.entity.User;
import com.example.usermanagement.service.UserService;
import com.mybatisflex.core.paginate.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户控制器
 *
 * @author example
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/user")
@Tag(name = "用户接口", description = "普通用户相关接口")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 获取当前用户信息
     *
     * @param userId 用户ID
     * @return 用户信息
     */
    @GetMapping("/profile")
    @Operation(summary = "获取当前用户信息", description = "获取当前登录用户的个人信息")
    public Result<User> getCurrentUser(
            @Parameter(description = "用户ID") @RequestParam Long userId) {
        try {
            User user = userService.findById(userId);
            if (user == null) {
                return Result.failed("用户不存在");
            }
            // 隐藏敏感信息
            user.setPassword(null);
            return Result.success(user);
        } catch (RuntimeException e) {
            return Result.failed(e.getMessage());
        }
    }
}
