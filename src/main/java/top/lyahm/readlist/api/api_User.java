package top.lyahm.readlist.api;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lyahm.readlist.utils.AccessUser;
import top.lyahm.readlist.vo.LoginForm;
import top.lyahm.readlist.vo.Result;

import static top.lyahm.readlist.utils.DbUser.*;

@RestController
@RequestMapping("/api")
public class api_User {
    @PostMapping("/checkLogin")
    public Result login(@RequestBody LoginForm form) {
        if (loginVerify(form.getUsername(), form.getPassword() )) {
            StpUtil.login(form.getUsername());
            return new Result(1, "登录成功, 即将跳转到管理面板");
        }
        return new Result(0, "用户名或密码错误");
    }

    @PostMapping("/checkRegister")
    public Result register(@RequestBody LoginForm form) {
        // TODO：根据重构后的 doRegister 进行重构
        if (registerCheck(form.getUsername()))  // 该用户已存在
        {
            return new Result(0, "注册失败，该用户已注册");
        } else {
            // 执行注册
            if (doRegiser(form.getUsername(), form.getPassword())) {
                return new Result(1, "注册成功，即将跳转到登录界面");
            } else {
                return new Result(0, "用户名或密码格式不符合要求");
            }
        }
    }

    /*
     * 将一个普通用户注册为管理员
     * 需要超级管理员权限
     */
    @PostMapping("/setAdmin")
    public Result setAdmin(@RequestBody LoginForm form) {
        // 这里直接重用了 LoginForm 的代码
        // 权限校验
        if (!AccessUser.haveAccess()) {
            return new Result(0, "无权限");
        }

        //TODO: 设置管理员

        return new Result(1, "设置成功");
    }

    @PostMapping("/rmUser")
    public Result rmUser(@RequestBody LoginForm form) {
        // 权限校验
        if (!AccessUser.haveAccess()) {
            return new Result(0, "无权限");
        }

        //TODO: 删除用户的代码

        return new Result(1, "删除成功");

    }
}
