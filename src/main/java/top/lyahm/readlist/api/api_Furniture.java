package top.lyahm.readlist.api;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lyahm.readlist.utils.DbFurniture;
import top.lyahm.readlist.utils.DbUpdateFurniture;
import top.lyahm.readlist.utils.DbUser;
import top.lyahm.readlist.vo.HandleParam;
import top.lyahm.readlist.vo.Result;

/**
 * 用于修改家居状态
 */
@SaCheckLogin
@RestController
@RequestMapping("/api")
public class api_Furniture {
    @RequestMapping("/editName")
    public Result editName(@RequestBody HandleParam data) {
        int Fid = data.Fid;
        String newName = data.getNewName();

        return DbUpdateFurniture.updateFurnitureName(Fid, newName);
    }

    @RequestMapping("/switchState")
    public Result switchState(@RequestBody HandleParam data) {
        int currState = data.getCurrState();
        int Fid = data.Fid;

        int changedState; // 改变后的状态
        if (currState == 2) {
            return new Result(0, "设备连接异常，无法操作");
        } else {
            changedState = (currState == 1) ? 0 : 1;
        }

        return DbUpdateFurniture.updateFurnitureStatue(Fid, changedState);
    }


    /**
     * 修改家居规则
     * @param data 相关数据
     * @return 返回操作结果
     */
    @RequestMapping("/editRule")
    public Result editRule(@RequestBody HandleParam data) {
        int variety = data.getVariety();
        int changeIndex = data.getChangeIndex();
        int Fid = data.Fid;
        int newValue = data.getNewValue();

        // 空调
        if (variety == 0) {
            // 修改温度上下限阈值
            if (changeIndex == 0) {
                return new Result(0, "开发中");
            }
            else if (changeIndex == 1) {
                return DbUpdateFurniture.updateAirconditionerMaxTemp(Fid, newValue);
            } else {
                return DbUpdateFurniture.updateAirconditionerMinTemp(Fid, newValue);
            }
        }

        // 冰箱
        else if (variety == 1) {
            // 修改冷藏温度
            if (changeIndex == 0) {
                return DbUpdateFurniture.updateRefrigeratorRefrigerationThreshold(Fid, newValue);
            }
            // 修改冷冻温度
            else {
                return DbUpdateFurniture.updateRefrigeratorFrozenThreshold(Fid, newValue);
            }
        }

        // 加湿器
        else if (variety == 2) {
            return DbUpdateFurniture.updateHumidifierThreshold(Fid, newValue);
        }

        // 窗帘
        else {
            return DbUpdateFurniture.updateCurtainThreshold(Fid, newValue);
        }
    }

    @RequestMapping("removeFurniture")
    public Result removeFurniture(@RequestBody HandleParam data) {
        int Fid = data.Fid;

        if (DbUser.verifyAdmin((String) StpUtil.getLoginId()) == 0) {
            return DbFurniture.rmFurniture(Fid);
        } else {
            return new Result(0, "需要管理员权限");
        }
    }

    @RequestMapping("moveFurniture")
    public Result moveFurniture(@RequestBody HandleParam data) {
        int Fid = data.Fid;
        int newRoomId = data.getNewRoomId();

        return DbFurniture.moveFurniture(Fid, newRoomId);
    }
}
