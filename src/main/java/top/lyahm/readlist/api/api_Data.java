package top.lyahm.readlist.api;

import cn.dev33.satoken.annotation.SaCheckLogin;
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
    @RequestMapping("/getListData")
    public ArrayList<AtoiData> getListData() {
        return DbData.getSensorData();
    }

    @RequestMapping("/gpt")
    public Result getGPTMsg() {
        try {
            String msg = DbFurniture.getGMSG().getMessage();
            return new Result(1, msg);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new Result(0, "出了点小问题，请稍后再试");
        }
    }

    /**
     * 返回家居状态统计信息
     * @return 包含三个整数的数组
     */
    @RequestMapping("/getState")
    public Integer[] getState() {
        return new Integer[]{1,2,3};
    }
}
