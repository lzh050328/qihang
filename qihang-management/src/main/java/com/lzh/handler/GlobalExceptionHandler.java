package com.lzh.handler;

import com.lzh.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e) {
        log.error("系统异常", e);
        return Result.error("服务器正忙");
    }
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public Result handleTypeMismatchException(MethodArgumentTypeMismatchException e) {
        log.error("参数格式不正确: {}", e.getName(), e);
        return Result.error("参数格式不正确: " + e.getName());
    }
    @ExceptionHandler(NoResourceFoundException.class)
    public Result handleNoResourceFoundException(NoResourceFoundException e) {
        log.warn("资源不存在: {}", e.getResourcePath());
        return Result.error("资源不存在: " + e.getResourcePath());
    }
    @ExceptionHandler(CustomException.class)
    public Result handleCustomException(CustomException e) {
        log.warn("业务异常: {}", e.getMessage());
        return Result.error(e.getMessage());
    }
}
