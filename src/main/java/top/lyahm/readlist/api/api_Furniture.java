package top.lyahm.readlist.api;

import cn.dev33.satoken.annotation.SaCheckLogin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import top.lyahm.readlist.utils.DbFurniture;
import top.lyahm.readlist.utils.DbUpdateFurniture;
import top.lyahm.readlist.vo.Result;

/**
 * 用于修改家居状态
 */
@SaCheckLogin
@RestController
@RequestMapping("/api")
public class api_Furniture {
    @RequestMapping("/editName")
    public Result editName(@RequestBody int Fid, @RequestBody String newName) {
        return DbUpdateFurniture.updateFurnitureName(Fid, newName);
    }

    @RequestMapping("/switchState")
    public Result switchState(@RequestBody int Fid, @RequestBody int currState) {
        int changedState; // 改变后的状态
        if (currState == 2) {
            return new Result(0, "设备连接异常，无法操作");
        } else {
            changedState = (currState == 1) ? 0 : 1;
        }

        return DbUpdateFurniture.updateFurnitureStatue(Fid, changedState);
    }


    /**
     * @param variety     家居种类(0为空调，1为冰箱，2为加湿器，3为窗帘）
     * @param Fid         要修改的家居id
     * @param changeIndex 要改变的阈值下标（要改变第几个阈值）
     * @param newValue    新的值
     * @return 返回操作结果
     */
    @RequestMapping("/editRule")
    public Result editRule(@RequestBody int variety, @RequestBody int Fid, @RequestBody int changeIndex, @RequestBody int newValue) {
        // 空调
        if (variety == 0) {
            // 修改温度上下限阈值
            if (changeIndex == 0) {
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
    public Result removeFurniture(@RequestBody int Fid) {
        return DbFurniture.rmFurniture(Fid);
    }

    @RequestMapping("moveFurniture")
    public Result moveFurniture(@RequestBody int Fid, @RequestBody int newRoomID) {
        return DbFurniture.moveFurniture(Fid, newRoomID);
    }
}
