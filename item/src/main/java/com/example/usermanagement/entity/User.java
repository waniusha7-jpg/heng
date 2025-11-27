package com.example.usermanagement.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 用户实体类
 *
 * @author example
 * @since 1.0.0
 */
@Data
@Table(value = "users", comment = "用户信息表")
public class User {

    /**
     * 用户ID
     */
    @Id(keyType = KeyType.Auto)
    @Column(comment = "用户ID")
    private Long id;

    /**
     * 用户名（唯一）
     */
    @Column(comment = "用户名（唯一）")
    private String username;

    /**
     * 邮箱（唯一）
     */
    @Column(comment = "邮箱（唯一）")
    private String email;

    /**
     * 加密后的密码
     */
    @Column(comment = "加密后的密码")
    private String password;

    /**
     * 角色（USER/ADMIN）
     */
    @Column(comment = "角色（USER/ADMIN）")
    private String role = "USER";

    /**
     * 状态（0:禁用, 1:启用）
     */
    @Column(comment = "状态（0:禁用, 1:启用）")
    private Integer status = 1;

    /**
     * 创建时间
     */
    @Column(comment = "创建时间")
    private LocalDateTime createTime;

    /**
     * 更新时间
     */
    @Column(comment = "更新时间")
    private LocalDateTime updateTime;

    /**
     * 逻辑删除标识（0:未删除, 1:已删除）
     */
    @Column(comment = "逻辑删除标识（0:未删除, 1:已删除）")
    private Integer deleted = 0;
}