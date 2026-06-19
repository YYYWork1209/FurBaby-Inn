package com.furbaby.furbaby.exception;


import com.furbaby.furbaby.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类，用于处理所有异常
 */
@Slf4j
@RestControllerAdvice
public class GlobalException {

    @ExceptionHandler(UnAuthorizedException.class)  // 处理未授权异常
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result handleUnAuthorizedException(UnAuthorizedException e) {
        log.error("未授权异常处理", e);
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleException(Exception e) {
        log.error("系统异常", e);
        return Result.error( "系统繁忙，请稍后重试");
    }

}
