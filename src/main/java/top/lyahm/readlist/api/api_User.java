package top.lyahm.readlist.api;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lyahm.readlist.utils.AccessUser;
import top.lyahm.readlist.vo.LoginForm;
import top.lyahm.readlist.vo.Result;

import java.util.Objects;

@RestController
@RequestMapping("/api")
public class api_User {
    @PostMapping("/checkLogin")
    public Result login(@RequestBody LoginForm form) {
//        if (DbUser.loginVerify(form.getUsername(), form.getPassword() )) {
        if (Objects.equals(form.getUsername(), "test") && Objects.equals(form.getPassword(), "e10adc3949ba59abbe56e057f20f883e")) {
            StpUtil.login(10001);
            return new Result(1, "登录成功, 即将跳转到管理面板");
        }
        return new Result(0, "用户名或密码错误");
    }

    @PostMapping("/checkRegister")
    public Result register(@RequestBody LoginForm form) {
        // 测试注册
        return new Result(1, "注册成功，即将跳转到登录界面");
    }

    /*
     * 将一个普通用户注册为管理员
     * 需要超级管理员权限
     */
    @PostMapping("/setAdmin")
    public Result setAdmin(@RequestBody LoginForm form) {
        // 这里直接重用了 LoginForm 的代码
        // 权限校验
        if(!AccessUser.haveAccess()) {
            return new Result(0, "无权限");
        }

        //TODO: 设置管理员

        return new Result(1, "设置成功");
    }

    @PostMapping("/rmUser")
    public Result rmUser(@RequestBody LoginForm form) {
        // 权限校验
        if(!AccessUser.haveAccess()) {
            return new Result(0, "无权限");
        }

        //TODO: 删除用户的代码

        return new Result(1, "删除成功");

    }
}
