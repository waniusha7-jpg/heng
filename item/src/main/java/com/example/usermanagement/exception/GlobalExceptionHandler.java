package com.example.usermanagement.exception;

import com.example.usermanagement.common.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author example
 * @since 1.0.0
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 处理运行时异常
     *
     * @param e 异常
     * @return 错误结果
     */
    @ExceptionHandler(RuntimeException.class)
    public Result<String> handleRuntimeException(RuntimeException e) {
        return Result.failed(e.getMessage());
    }

    /**
     * 处理所有异常
     *
     * @param e 异常
     * @return 错误结果
     */
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        return Result.failed("系统内部错误：" + e.getMessage());
    }
}