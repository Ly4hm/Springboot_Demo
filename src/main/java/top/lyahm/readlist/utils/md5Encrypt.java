package top.lyahm.readlist.utils;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

//普通md5加密类，开头为0会舍去
public class md5Encrypt{
        public static String encode(String pwd){
            try {
                MessageDigest md = MessageDigest.getInstance("MD5");// 生成一个MD5加密计算摘要
                md.update(pwd.getBytes());// 计算md5函数
                return new BigInteger(1, md.digest()).toString(16);
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
            return pwd;
        }
}
