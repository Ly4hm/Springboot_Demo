package top.lyahm.readlist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lyahm.readlist.utils.DbUtil;

@RestController
public class HelloWorld {

    @GetMapping("/search")
    public String hello() {
        try {
            DbUtil db = new DbUtil();
            System.out.println("selecting......");
            return DbUtil.echoResult(db.getColumn("ENo", "Phone", "EName"));
        }
        catch (Exception e) {
            return "wrong";
        }
    }
}
