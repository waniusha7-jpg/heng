# 用户管理系统

基于 Spring Boot 3.x、MyBatis-Flex 和 Hutool 开发的用户管理模块，提供完整的用户注册、登录、权限管理功能。

## 技术栈

- **后端框架**: Spring Boot 3.x (兼容 Jakarta EE 9+)
- **ORM 框架**: MyBatis-Flex
- **工具库**: Hutool (密码加密、JSON 处理、JWT 生成等)
- **数据库**: MySQL 8.0+
- **接口文档**: Knife4j (Swagger UI 增强版)
- **安全机制**: 基于 Token 的无状态认证

## 功能特性

### 1. 用户注册
- 用户名、邮箱唯一校验
- 邮箱格式验证
- 密码长度至少6位
- 密码使用 BCrypt 加密存储
- 默认角色为 USER，状态为启用

### 2. 用户登录
- 用户名/密码验证
- 生成 JWT Token
- Token 有效期 2 小时
- 返回 Token 和用户信息

### 3. 权限管理
- 基于 Token 的无状态认证
- 普通用户只能访问 `/api/user/**` 接口
- 管理员可访问所有接口
- 使用拦截器实现权限校验

### 4. 管理员功能
- 分页查询所有用户
- 禁用/启用用户
- 逻辑删除用户

## 快速开始

### 1. 环境准备

- JDK 17+
- MySQL 8.0+
- Maven 3.6+

### 2. 数据库配置

1. 创建数据库：
```sql
CREATE DATABASE IF NOT EXISTS user_management DEFAULT CHARSET utf8mb4 COLLATE utf8mb4_unicode_ci;
```

2. 执行 SQL 脚本：
```bash
mysql -u root -p user_management < sql/create_table.sql
```

3. 修改 `application.yml` 中的数据库配置：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/user_management?useUnicode=true&characterEncoding=utf8mb4&useSSL=false&serverTimezone=Asia/Shanghai
    username: your_username
    password: your_password
```

### 3. 启动项目

```bash
mvn spring-boot:run
```

### 4. 访问接口文档

项目启动后，访问 Knife4j 在线文档：
```
http://localhost:8080/doc.html
```

## API 接口列表

### 认证接口

| 接口路径 | 请求方法 | 描述 | 参数 | 响应示例 |
|---------|---------|------|------|---------|
| `/api/auth/register` | POST | 用户注册 | `username`, `email`, `password` | `{"code":200,"message":"注册成功","data":{"userId":1,"username":"test","email":"test@example.com"}}` |
| `/api/auth/login` | POST | 用户登录 | `username`, `password` | `{"code":200,"message":"登录成功","data":{"token":"xxx","userId":1,"username":"test","role":"USER"}}` |
| `/api/auth/logout` | POST | 用户注销 | - | `{"code":200,"message":"注销成功","data":null}` |

### 用户接口

| 接口路径 | 请求方法 | 描述 | 参数 | 响应示例 |
|---------|---------|------|------|---------|
| `/api/user/profile` | GET | 获取当前用户信息 | `userId` | `{"code":200,"message":"success","data":{"id":1,"username":"test","email":"test@example.com","role":"USER","status":1}}` |

### 管理员接口

| 接口路径 | 请求方法 | 描述 | 参数 | 响应示例 |
|---------|---------|------|------|---------|
| `/api/admin/users` | GET | 分页查询所有用户 | `page`, `size` | `{"code":200,"message":"success","data":{"records":[],"total":0,"page":1,"size":10}}` |
| `/api/admin/users/{id}/status` | PUT | 更新用户状态 | `status` | `{"code":200,"message":"状态更新成功","data":{"id":1,"username":"test","status":0}}` |
| `/api/admin/users/{id}` | DELETE | 删除用户 | - | `{"code":200,"message":"删除成功","data":null}` |

## 项目结构

```
user-management/
├─ sql/                    # SQL 脚本
│  └─ create_table.sql    # 建表语句
├─ src/
│  ├─ main/
│  │  ├─ java/
│  │  │  └─ com/example/usermanagement/
│  │  │     ├─ common/       # 公共类
│  │  │     ├─ config/       # 配置类
│  │  │     ├─ controller/   # 控制器
│  │  │     ├─ entity/       # 实体类
│  │  │     ├─ exception/    # 异常处理
│  │  │     ├─ interceptor/  # 拦截器
│  │  │     ├─ mapper/       # Mapper 接口
│  │  │     ├─ service/      # 服务层
│  │  │     └─ util/         # 工具类
│  │  └─ resources/
│  │     ├─ mapper/          # Mapper XML 文件
│  │     └─ application.yml  # 配置文件
│  └─ test/                  # 测试类
├─ pom.xml                   # Maven 配置
└─ README.md                 # 项目说明
```

## 核心代码示例

### 1. 用户注册

```java
@PostMapping("/register")
public Result<Map<String, Object>> register(@RequestBody RegisterRequest registerRequest) {
    User user = userService.register(
            registerRequest.getUsername(),
            registerRequest.getEmail(),
            registerRequest.getPassword()
    );
    // ...
}
```

### 2. JWT Token 生成

```java
public String generateToken(Long userId, String username, String role) {
    Map<String, Object> payload = new HashMap<>();
    payload.put(JWTPayload.ISSUED_AT, new Date());
    payload.put(JWTPayload.EXPIRES_AT, DateUtil.offsetMillisecond(new Date(), (int) expireTime));
    payload.put("userId", userId);
    payload.put("username", username);
    payload.put("role", role);
    return JWTUtil.createToken(payload, secret.getBytes());
}
```

### 3. 权限拦截器

```java
@Override
public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
    String token = getTokenFromRequest(request);
    if (!jwtUtil.validateToken(token)) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        return false;
    }
    // 权限检查
    // ...
}
```

## 默认管理员账号

```
用户名: admin
密码: admin123
邮箱: admin@example.com
角色: ADMIN
```

## 注意事项

1. JWT 密钥配置：请在 `application.yml` 中修改 `jwt.secret` 为安全的密钥
2. Token 有效期：默认 2 小时，可在 `application.yml` 中修改 `jwt.expire-time`
3. 密码加密：使用 Hutool 的 BCrypt 加密，安全可靠
4. 权限控制：普通用户只能访问 `/api/user/**` 接口，管理员可访问所有接口

## 许可证

Apache License 2.0
