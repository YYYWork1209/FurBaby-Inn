package com.furbaby.furbaby.exception;


import com.furbaby.furbaby.entity.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

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

    @ExceptionHandler(NoResourceFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Result handleNoResourceFoundException(NoResourceFoundException e) {
        return Result.error("资源不存在");
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public Result handleException(Exception e) {
        log.error("系统异常", e);
        return Result.error( "系统繁忙，请稍后重试");
    }

    @ExceptionHandler(NoRegisterException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result handleNoRegisterException(NoRegisterException e) {
        log.error("未注册异常处理", e);
        return Result.error(e.getMessage());
    }

    @ExceptionHandler(PhoneOrPasswordException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public Result handlePhoneOrPasswordException(PhoneOrPasswordException e) {
        log.error("手机号或密码错误异常处理", e);
        return Result.error(e.getMessage());
    }

}
