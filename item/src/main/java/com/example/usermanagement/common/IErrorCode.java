package com.example.usermanagement.common;

/**
 * 错误码接口
 *
 * @author example
 * @since 1.0.0
 */
public interface IErrorCode {

    /**
     * 获取错误码
     *
     * @return 错误码
     */
    Integer getCode();

    /**
     * 获取错误信息
     *
     * @return 错误信息
     */
    String getMessage();
}