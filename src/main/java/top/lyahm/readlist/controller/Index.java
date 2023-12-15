package top.lyahm.readlist.controller;

import cn.dev33.satoken.stp.StpUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import top.lyahm.readlist.utils.DbFurniture;
import top.lyahm.readlist.utils.DbUser;
import top.lyahm.readlist.vo.*;

import java.util.ArrayList;

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

        // 返回的用户列表
        ArrayList<User> vo_user_list = DbUser.getAllUser();

        // 返回家具信息列表
        ArrayList<airConditionerData> airConditioners = DbFurniture.getAirData();
        ArrayList<RefrigeratorData> Refrigerators = DbFurniture.getRefrigeratorData();
        ArrayList<HumidifierData> Humidifiers = DbFurniture.getHumiDifierData();
        ArrayList<CurtainData> Curtains = DbFurniture.getCurtainData();


        // 填入 model 对象
        model.addAttribute("user", vo_user);
        model.addAttribute("user_list", vo_user_list);
        // 家居列表信息填入
        model.addAttribute("airConditioners", airConditioners);
        model.addAttribute("Refrigerators", Refrigerators);
        model.addAttribute("Humidifiers", Humidifiers);
        model.addAttribute("Curtains", Curtains);

        return "index";
    }

    @GetMapping("/error")
    public String error() {
        return "error";
    }
}
