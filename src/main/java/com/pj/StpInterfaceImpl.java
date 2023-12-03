package com.pj;

import cn.dev33.satoken.stp.StpInterface;
import org.springframework.stereotype.Component;
import top.lyahm.readlist.utils.DbUser;

import java.util.ArrayList;
import java.util.List;

/*
自定义权限加载接口实现类
 */
@Component
public class StpInterfaceImpl implements StpInterface {
    // TODO: 重构该接口

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 未使用该机制，故不添加任何权限
        return new ArrayList<>();
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<String> list = new ArrayList<>();
        if (DbUser.verifyAdmin((String) loginId) == 0) {
            list.add("super-admin");
            System.out.println("admin 权限角色添加");
        }
        return list;
    }
}
