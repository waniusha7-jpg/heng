package com.example.usermanagement.service.impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.example.usermanagement.entity.User;
import com.example.usermanagement.mapper.UserMapper;
import com.example.usermanagement.service.UserService;
import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static com.example.usermanagement.entity.table.UserTableDef.USER;

/**
 * 用户服务实现类
 *
 * @author example
 * @since 1.0.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User register(String username, String email, String password) {
        // 校验用户名是否已存在
        if (userMapper.findByUsername(username) != null) {
            throw new RuntimeException("用户名已存在");
        }

        // 校验邮箱是否已存在
        if (userMapper.findByEmail(email) != null) {
            throw new RuntimeException("邮箱已存在");
        }

        // 校验密码长度
        if (StrUtil.length(password) < 6) {
            throw new RuntimeException("密码长度至少6位");
        }

        // 创建用户
        User user = new User();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(SecureUtil.md5(password)); // 使用MD5加密密码（临时解决方案）
        user.setRole("USER");
        user.setStatus(1);
        user.setCreateTime(LocalDateTime.now());
        user.setUpdateTime(LocalDateTime.now());
        user.setDeleted(0);

        userMapper.insert(user);
        return user;
    }

    @Override
    public User login(String username, String password) {
        User user = userMapper.findByUsername(username);
        if (user == null) {
            throw new RuntimeException("用户名不存在");
        }

        if (user.getStatus() == 0) {
            throw new RuntimeException("用户已被禁用");
        }

        if (!SecureUtil.md5(password).equals(user.getPassword())) {
            throw new RuntimeException("密码错误");
        }

        return user;
    }

    @Override
    public User findById(Long id) {
        return userMapper.selectOneById(id);
    }

    @Override
    public User findByUsername(String username) {
        return userMapper.findByUsername(username);
    }

    @Override
    public Page<User> findAllUsers(int page, int size) {
        QueryWrapper queryWrapper = QueryWrapper.create()
                .eq("deleted", 0)
                .orderBy("create_time", false);
        return userMapper.paginate(page, size, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User updateUserStatus(Long id, Integer status) {
        User user = userMapper.selectOneById(id);
        if (user == null || user.getDeleted() == 1) {
            throw new RuntimeException("用户不存在");
        }

        user.setStatus(status);
        user.setUpdateTime(LocalDateTime.now());
        userMapper.update(user);
        return user;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteUser(Long id) {
        User user = userMapper.selectOneById(id);
        if (user == null || user.getDeleted() == 1) {
            throw new RuntimeException("用户不存在");
        }

        User updateUser = new User();
        updateUser.setId(id);
        updateUser.setDeleted(1);
        updateUser.setUpdateTime(LocalDateTime.now());
        userMapper.update(updateUser);
    }
}