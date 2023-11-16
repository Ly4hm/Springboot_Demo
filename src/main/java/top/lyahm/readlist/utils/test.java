package top.lyahm.readlist.utils;

//测试BCryptUtil
public class test {
    public static void main(String[] args){
        String pwd=null;
        System.out.println(BCryptUtil.encode(pwd));

        System.out.println(BCryptUtil.match(pwd,"$2a$10$7qGGdTiLfmVVFan2a5u5POLkCJKrdnvUqdP.7cnO1c4kU6wYDoDkC"));
    }
}
