package top.lyahm.readlist.api;

import cn.dev33.satoken.annotation.SaCheckLogin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lyahm.readlist.utils.DbData;
import top.lyahm.readlist.utils.DbFurniture;
import top.lyahm.readlist.vo.AtoiData;
import top.lyahm.readlist.vo.Result;

import java.util.ArrayList;

@SaCheckLogin
@RestController
@RequestMapping("/api")
public class api_Data {
    @PostMapping("/getListData")
    public ArrayList<AtoiData> getListData() {
        return DbData.getSensorData();
    }

    @PostMapping("/gpt")
    public Result getGPTMsg() {
        try {
            String msg = DbFurniture.getGMSG().getMessage();
            return new Result(1, msg);
        } catch (Exception e) {
            return new Result(0, "出了点小问题，稍后再试吧");
        }
    }
}
