package top.lyahm.readlist.api;

import cn.dev33.satoken.annotation.SaCheckLogin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lyahm.readlist.vo.Result;

/**
 * 用于修改家居状态
 */
@SaCheckLogin
@RestController
@RequestMapping("/api")
public class api_Furniture {
    @RequestMapping("/editName")
    public Result editName() {
        return new Result(1, "test successfully");
    }

    @RequestMapping("/switchState")
    public Result switchState() {
        return new Result(1, "test successfully");
    }

    @RequestMapping("/editRule")
    public Result editRule() {
        return new Result(1, "test successfully");
    }

    @RequestMapping("removeFurniture")
    public Result removeFurniture() {
        return new Result(1, "test successfully");
    }

    @RequestMapping("moveFurniture")
    public Result moveFurniture() {
        return new Result(1, "test successfully");
    }
}
