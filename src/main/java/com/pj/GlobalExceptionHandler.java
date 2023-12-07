package com.pj;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.NotRoleException;
import cn.dev33.satoken.util.SaResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import top.lyahm.readlist.vo.Result;

@RestControllerAdvice
public class GlobalExceptionHandler {
    // 权限缺失
    @ExceptionHandler(NotRoleException.class)
    public Result handlerException(NotRoleException e) {
        e.printStackTrace();
        return new Result(0, "无权限");
    }

    // 未登录访问
    @ExceptionHandler(NotLoginException.class)
    public SaResult handlerException(NotLoginException e) {
        e.printStackTrace();
        return SaResult.error(e.getMessage());
    }

    // 其他异常拦截
    @ExceptionHandler(Exception.class)
    public SaResult handlerException(Exception e) {
        e.printStackTrace();
        return SaResult.error(e.getMessage());
    }
}
