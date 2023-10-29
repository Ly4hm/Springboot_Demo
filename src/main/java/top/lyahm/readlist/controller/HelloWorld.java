package top.lyahm.readlist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;
import top.lyahm.readlist.utils.DbUtil;

@RestController
public class HelloWorld {

    @GetMapping("/error")
    public RedirectView error() {
        return new RedirectView("/selectAll");
    }

    /* 通过无参数请求来查询 tmp 表的全部数据
     */
    @GetMapping("/selectAll")
    public String selectAll() {
        try {
            DbUtil db = new DbUtil();
            System.out.println("selecting......");
            return DbUtil.echoResult(db.getColumn("tmp", "name", "age")).replace("\n", "</br>");
        }
        catch (Exception e) {
            return "wrong";
        }
    }

    /* 需要 GET 参数传入 name, age 实现 tmp 的插入
     */
    @GetMapping("/insertToTmp")
    public RedirectView insertToTmp(@RequestParam String name, @RequestParam Integer age) {
        try {
            DbUtil db = new DbUtil();
            db.insertToTmp(name, age);
            return new RedirectView("/selectAll");
        }
        catch (Exception e) {
            return new RedirectView("/selectAll");
        }
    }

    /* 通过 GET 传参传入 name 值，实现指定 name 的删除
     */
    @GetMapping("/deleteFromTmp")
    public RedirectView deleteFromTmp(@RequestParam String name) {
        try {
            DbUtil db = new DbUtil();
            db.deleteFromTmp(name);
            return new RedirectView("/selectAll");
        }
        catch (Exception e) {
            return new RedirectView("/selectAll");
        }
    }

    @GetMapping("/updateFromTmp")
    public RedirectView updateFromTmp(@RequestParam String name, @RequestParam Integer age) {
        try {
            DbUtil db = new DbUtil();
            db.updateFromTmp(name, age);
            return new RedirectView("/selectAll");
        }
        catch (Exception e) {
            return new RedirectView("/selectAll");
        }
    }
}
