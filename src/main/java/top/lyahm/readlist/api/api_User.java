package top.lyahm.readlist.api;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.web.bind.annotation.*;
import top.lyahm.readlist.utils.DbUser;
import top.lyahm.readlist.vo.LoginForm;
import top.lyahm.readlist.vo.Result;
import top.lyahm.readlist.vo.User;
import top.lyahm.readlist.vo.resetEmailData;

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
        if (registerCheck(form.getUsername()))  // 该用户已存在
        {
            return new Result(0, "注册失败，该用户已注册");
        } else {
            // 执行注册
            Result r = doRegiser(form.getUsername(), form.getPassword());
            if (r.getCode() == 0) {
                return r;  // 直接返回 api 抛出的错误
            } else {
                return new Result(1, "注册成功，即将跳转到登录界面");
            }
        }
    }

    /*
     * 将一个普通用户注册为管理员
     * 需要超级管理员权限
     */
    @SaCheckRole("super-admin")
    @PostMapping("/setAdmin")
    public Result setAdmin(@RequestBody LoginForm form) {
        // 这里直接重用了 LoginForm 的数据框架
        if (DbUser.setAdmin(form.getUsername())) {
            return new Result(1, "设置成功");
        } else {
            return new Result(0, "发生了些小意外");
        }
    }

    @SaCheckRole("super-admin")
    @PostMapping("/unsetAdmin")
    public Result unsetAdmin(@RequestBody LoginForm form) {
        // 解除管理员
        if (DbUser.unsetAdmin(form.getUsername())) {
            return new Result(1, "解除成功");
        } else {
            return new Result(0, "发生了点小意外");
        }
    }

    @SaCheckRole("super-admin")
    @PostMapping("/rmUser")
    public Result rmUser(@RequestBody LoginForm form) {
        // 删除用户
        Result result = DbUser.rmUser(form.getUsername());
        // 返回消息处理
        if (result.getCode() == 1) {
            return new Result(1, "删除成功");
        } else {
            return result;
        }
    }

    @SaCheckLogin
    @GetMapping("/getSelfInfo")
    public User getSelfInfo(@RequestParam String username) {
        // 这个地方多此一举的做了一个过滤，让用户只能访问自己的信息，是考虑到以后可能拓展，防止未来的信息泄露
        if (StpUtil.getLoginId().equals(username)) {
            return DbUser.getUserInfo(username);
        } else {
            return new User();
        }
    }

    @SaCheckLogin
    @PostMapping("/resetEmail")
    public Result resetEmail(@RequestBody resetEmailData formData) {
        String username = formData.getUsername();
        String email = formData.getEmail();

        // 进行一次鉴权
        if (StpUtil.getLoginId().equals(username)) {
            return DbUser.resetUserEmail(username, email);
        } else {
            System.out.println(StpUtil.getLoginId().equals(username));
            return new Result(0, "呦呵，还想改别人的邮箱？");
        }
    }
}
