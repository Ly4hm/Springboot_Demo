package top.lyahm.readlist.controller;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class Login {
    @GetMapping("/index")
    public String index() {
        // 验证是否登录
        if (!StpUtil.isLogin()) {
            return "redirect:/login";
        }
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        // 验证是否登录
        if (StpUtil.isLogin()) {
            return "redirect:/index";
        }
        return "login";
    }

    @GetMapping("/logout")
    public String logout() {
        StpUtil.logout();
        return "redirect:/login";
    }
}
