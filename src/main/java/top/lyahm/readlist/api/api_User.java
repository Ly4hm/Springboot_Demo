package top.lyahm.readlist.api;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lyahm.readlist.vo.LoginForm;
import top.lyahm.readlist.vo.Result;

@RestController
@RequestMapping("/api")
public class api_User {
    @PostMapping("/checkLogin")
    public Result login(@RequestBody LoginForm form) {
        // 测试语句
        if ("test".equals(form.getUsername()) && "123456".equals(form.getPassword())) {
            StpUtil.login(10001);

            return new Result(1, "登录成功");
        }
        return new Result(0, "登录失败");
    }

    @PostMapping("/checkRegister")
    public Result register(@RequestBody LoginForm form) {
        // 测试注册
        return new Result(1, "登录成功");
    }
}
