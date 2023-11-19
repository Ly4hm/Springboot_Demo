package top.lyahm.readlist.utils;

//测试BCryptUtil
public class test {
    public static void main(String[] args){
        String pwd="e10adc3949ba59abbe56e057f20f883e";
        System.out.println(BCryptUtil.encode(pwd));

        System.out.println(BCryptUtil.match(pwd,"$2a$10$0UA9c9E/qpLonFSkJERpWOryjPbNVzgLSiN6jZgyHp5iOvHnS0EsC"));
    }
}
