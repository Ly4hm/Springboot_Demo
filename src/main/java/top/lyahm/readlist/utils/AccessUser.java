package top.lyahm.readlist.utils;

import cn.dev33.satoken.exception.NotRoleException;
import cn.dev33.satoken.stp.StpUtil;


/*
封装一些用户操作的方法
 */
public class AccessUser {
    // 判断用户是否具有超级管理员权限
    public static Boolean haveAccess() {
        if (!StpUtil.isLogin()) {
            return false;
        }
        // 超级管理员验证
        try {
            StpUtil.checkRole("super-admin");
        } catch (NotRoleException e) {
            return false;
        }
        return true;
    }
}