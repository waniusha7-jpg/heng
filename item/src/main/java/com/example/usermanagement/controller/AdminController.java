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

import java.util.Map;

/**
 * 管理员控制器
 *
 * @author example
 * @since 1.0.0
 */
@RestController
@RequestMapping("/api/admin")
@Tag(name = "管理员接口", description = "管理员管理用户相关接口")
public class AdminController {

    @Autowired
    private UserService userService;

    /**
     * 分页查询所有用户
     *
     * @param page 页码
     * @param size 每页大小
     * @return 用户列表
     */
    @GetMapping("/users")
    @Operation(summary = "分页查询所有用户", description = "分页查询所有用户信息")
    public Result<Page<User>> getAllUsers(
            @Parameter(description = "页码") @RequestParam(defaultValue = "1") int page,
            @Parameter(description = "每页大小") @RequestParam(defaultValue = "10") int size) {
        try {
            Page<User> userPage = userService.findAllUsers(page, size);
            // 隐藏敏感信息
            userPage.getRecords().forEach(user -> user.setPassword(null));
            return Result.success(userPage);
        } catch (RuntimeException e) {
            return Result.failed(e.getMessage());
        }
    }

    /**
     * 更新用户状态
     *
     * @param id     用户ID
     * @param status 状态（0:禁用, 1:启用）
     * @return 更新后的用户信息
     */
    @PutMapping("/users/{id}/status")
    @Operation(summary = "更新用户状态", description = "禁用或启用用户")
    public Result<User> updateUserStatus(
            @Parameter(description = "用户ID") @PathVariable Long id,
            @Parameter(description = "状态") @RequestBody Map<String, Integer> statusRequest) {
        try {
            Integer status = statusRequest.get("status");
            if (status == null || (status != 0 && status != 1)) {
                return Result.validateFailed("状态参数错误，必须为0或1");
            }

            User user = userService.updateUserStatus(id, status);
            user.setPassword(null);
            return Result.success(user, "状态更新成功");
        } catch (RuntimeException e) {
            return Result.failed(e.getMessage());
        }
    }

    /**
     * 删除用户（逻辑删除）
     *
     * @param id 用户ID
     * @return 删除结果
     */
    @DeleteMapping("/users/{id}")
    @Operation(summary = "删除用户", description = "逻辑删除用户")
    public Result<String> deleteUser(
            @Parameter(description = "用户ID") @PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return Result.success(null, "删除成功");
        } catch (RuntimeException e) {
            return Result.failed(e.getMessage());
        }
    }
}
