package com.example.usermanagement.service;

import com.example.usermanagement.entity.User;
import com.mybatisflex.core.paginate.Page;

/**
 * 用户服务接口
 *
 * @author example
 * @since 1.0.0
 */
public interface UserService {

    /**
     * 用户注册
     *
     * @param username 用户名
     * @param email    邮箱
     * @param password 密码
     * @return 注册成功的用户信息
     */
    User register(String username, String email, String password);

    /**
     * 用户登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录成功的用户信息
     */
    User login(String username, String password);

    /**
     * 根据ID查询用户
     *
     * @param id 用户ID
     * @return 用户信息
     */
    User findById(Long id);

    /**
     * 根据用户名查询用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    User findByUsername(String username);

    /**
     * 分页查询所有用户
     *
     * @param page 页码
     * @param size 每页大小
     * @return 分页用户列表
     */
    Page<User> findAllUsers(int page, int size);

    /**
     * 更新用户状态
     *
     * @param id     用户ID
     * @param status 状态（0:禁用, 1:启用）
     * @return 更新后的用户信息
     */
    User updateUserStatus(Long id, Integer status);

    /**
     * 删除用户（逻辑删除）
     *
     * @param id 用户ID
     */
    void deleteUser(Long id);
}