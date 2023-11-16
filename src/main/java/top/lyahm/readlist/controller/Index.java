package top.lyahm.readlist.controller;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import top.lyahm.readlist.vo.User;

@Controller
public class Index {
    @GetMapping
    public String root() {
        return "redirect:/index";
    }

    @GetMapping("/index")
    public String index(Model model) {
        // 验证是否登录
        if (!StpUtil.isLogin()) {
            return "redirect:/login";
        }

        // 填充渲染数据
        User vo_user = new User();
        vo_user.setName(StpUtil.getLoginIdAsString());
        model.addAttribute("user",vo_user);

        return "index";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }
}
