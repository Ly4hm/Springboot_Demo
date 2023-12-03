package top.lyahm.readlist.utils;

import cn.dev33.satoken.secure.BCrypt;

public class BCryptUtil {
    //    加密密码
    public static String encode(String password) {
        return BCrypt.hashpw(password,BCrypt.gensalt());
    }

    //    匹配密码是否一致
    public static boolean match(String password, String encodePassword) {
        return BCrypt.checkpw(password, encodePassword);
    }

//    public static void main(String[] args){
//        System.out.println(encode("63cfe4f7d97d3172628b386d15d5365f"));
//    }
}
