package com.pj;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GlobalExceptionHandler {
    // 全局异常拦截
    @ExceptionHandler
    public String handlerException(Exception e) {
        e.printStackTrace();
        System.out.println("出错，全局过滤器抛出以上错误");
        return "redirect:/error";
    }
}
