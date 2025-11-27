use db07
-- 用户表结构
CREATE TABLE `users` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `username` varchar(50) NOT NULL COMMENT '用户名（唯一）',
  `email` varchar(100) NOT NULL COMMENT '邮箱（唯一）',
  `password` varchar(255) NOT NULL COMMENT '加密后的密码',
  `role` varchar(20) DEFAULT 'USER' COMMENT '角色（USER/ADMIN）',
  `status` tinyint DEFAULT '1' COMMENT '状态（0:禁用, 1:启用）',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` tinyint DEFAULT '0' COMMENT '逻辑删除标识（0:未删除, 1:已删除）',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_username` (`username`),
  UNIQUE KEY `uk_email` (`email`),
  KEY `idx_role` (`role`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户信息表';

-- 插入管理员用户（密码：admin123）
INSERT INTO `users` (`username`, `email`, `password`, `role`, `status`) 
VALUES ('admin', 'admin@example.com', '$2a$10$6V8qazb7cdeFghijKlMnopQrstuvwxyzABCDEFGHIJKLMN', 'ADMIN', 1);