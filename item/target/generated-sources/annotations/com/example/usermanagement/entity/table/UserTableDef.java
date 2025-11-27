package com.example.usermanagement.entity.table;

import com.mybatisflex.core.query.QueryColumn;
import com.mybatisflex.core.table.TableDef;

// Auto generate by mybatis-flex, do not modify it.
public class UserTableDef extends TableDef {

    /**
     * 用户实体类

 @author example
 @since 1.0.0
     */
    public static final UserTableDef USER = new UserTableDef();

    /**
     * 用户ID
     */
    public final QueryColumn ID = new QueryColumn(this, "id");

    /**
     * 角色（USER/ADMIN）
     */
    public final QueryColumn ROLE = new QueryColumn(this, "role");

    /**
     * 邮箱（唯一）
     */
    public final QueryColumn EMAIL = new QueryColumn(this, "email");

    /**
     * 状态（0:禁用, 1:启用）
     */
    public final QueryColumn STATUS = new QueryColumn(this, "status");

    /**
     * 逻辑删除标识（0:未删除, 1:已删除）
     */
    public final QueryColumn DELETED = new QueryColumn(this, "deleted");

    /**
     * 加密后的密码
     */
    public final QueryColumn PASSWORD = new QueryColumn(this, "password");

    /**
     * 用户名（唯一）
     */
    public final QueryColumn USERNAME = new QueryColumn(this, "username");

    /**
     * 创建时间
     */
    public final QueryColumn CREATE_TIME = new QueryColumn(this, "create_time");

    /**
     * 更新时间
     */
    public final QueryColumn UPDATE_TIME = new QueryColumn(this, "update_time");

    /**
     * 所有字段。
     */
    public final QueryColumn ALL_COLUMNS = new QueryColumn(this, "*");

    /**
     * 默认字段，不包含逻辑删除或者 large 等字段。
     */
    public final QueryColumn[] DEFAULT_COLUMNS = new QueryColumn[]{ID, ROLE, EMAIL, STATUS, DELETED, PASSWORD, USERNAME, CREATE_TIME, UPDATE_TIME};

    public UserTableDef() {
        super("", "users");
    }

    private UserTableDef(String schema, String name, String alisa) {
        super(schema, name, alisa);
    }

    public UserTableDef as(String alias) {
        String key = getNameWithSchema() + "." + alias;
        return getCache(key, k -> new UserTableDef("", "users", alias));
    }

}
