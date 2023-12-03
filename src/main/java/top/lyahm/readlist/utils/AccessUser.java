package top.lyahm.readlist.utils;

import cn.dev33.satoken.exception.NotRoleException;
import cn.dev33.satoken.stp.StpUtil;


/*
封装一些用户操作的方法
 */
public class AccessUser {
    // 判断用户是否具有超级管理员权限
    // TODO: 处理这个函数的bug，无法正常鉴权
    public static Boolean haveAccess() {
        if (!StpUtil.isLogin()) {
            return false;
        }
        // 超级管理员验证
        if (StpUtil.hasRole("super-admin")) {
            System.out.println("有管理员权限");
            return true;
        } else {
            System.out.println("无管理员权限");
            return false;
        }
    }
}
