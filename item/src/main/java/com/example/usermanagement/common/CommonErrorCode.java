package com.example.usermanagement.common;

/**
 * 通用错误码
 *
 * @author example
 * @since 1.0.0
 */
public enum CommonErrorCode implements IErrorCode {

    /**
     * 成功
     */
    SUCCESS(200, "操作成功"),

    /**
     * 失败
     */
    FAILED(500, "操作失败"),

    /**
     * 参数验证失败
     */
    VALIDATE_FAILED(400, "参数验证失败"),

    /**
     * 未登录
     */
    UNAUTHORIZED(401, "未登录或登录已过期"),

    /**
     * 无权限
     */
    FORBIDDEN(403, "无权限访问");

    private final Integer code;
    private final String message;

    CommonErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}