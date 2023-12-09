package top.lyahm.readlist.utils;

import top.lyahm.readlist.vo.Result;
import top.lyahm.readlist.vo.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbUser{
    private static final Logger LOGGER = Logger.getLogger(DbUser.class.getName());
//    执行插入操作，用jbcrypt加密密码（60位）
    public static Result doRegiser(String username, String password){
        int code=0;
        Connection conn=null;
        PreparedStatement pstmt=null;

//        对用户名和密码格式长度进行限制
        String usernamePattern="\\w{1,20}";

        if(!username.matches(usernamePattern)){
            String message="用户名格式不符合要求";
            return new Result(code,message);
        }

        if(registerCheck(username)){
            String message="用户名已存在";
            return new Result(code,message);
        }

        String encodedpassword;
//        md5加密用户id，避免id重复，32位
        String encrypedtid=md5Encrypt.encode(username);
        encodedpassword=BCryptUtil.encode(password);
//        默认普通用户
        int access=2;

//        预编译sql语句
        try{
            conn = DbUtil.getConnection();
            String sql="INSERT INTO User(ID,NAME,ACCESS,PASSWORD) VALUES (?,?,?,?)";
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1,encrypedtid);
            pstmt.setString(2,username);
            pstmt.setInt(3,access);
            pstmt.setString(4,encodedpassword);

            int affectedRows=pstmt.executeUpdate();
            if (affectedRows > 0) {
                code=1;
                String message="插入成功";
                return new Result(code,message);
            } else {
                return new Result(code,"插入失败，受影响行数为0");
            }
        }catch (SQLException e) {
            // 记录数据库异常信息
            LOGGER.log(Level.SEVERE,"数据库异常： "+ e.getMessage(),e);
        }finally {
            DbUtil.release(conn, pstmt, null);
        }
        return new Result(code,"没有成功");
    }

//    根据用户名验证用户是否存在
    public static boolean registerCheck(String username) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DbUtil.getConnection();
            String sql = "SELECT COUNT(*) FROM User WHERE name=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // 如果 count 大于 0，表示用户名已存在
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,"数据库异常： "+ e.getMessage(),e);
        } finally {
            DbUtil.release(conn, pstmt, rs);
        }

        return false; // 如果发生异常，默认返回 false
    }

    public static ArrayList<User> getAllUser() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        ArrayList<User> userList = new ArrayList<>();

        try {
            conn = DbUtil.getConnection();
            String sql = "SELECT name,email,access FROM User WHERE access!=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1,0);

            rs = pstmt.executeQuery();

            while(rs.next()) {
                User user=new User();
                user.setName(rs.getString(1));
                user.setEmail(rs.getString(2));
                user.setAccess(rs.getInt(3));
//                如果access码为0，则设置isAdmin为true
                user.setAdmin(rs.getInt(3) == 1);
//                如果 email 的值不为null，则设置haveEmail为true
                user.setHaveEmail(rs.getString(2) != null);
                userList.add(user);
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,"数据库异常： "+ e.getMessage(),e);
        } finally {
            DbUtil.release(conn, pstmt, rs);
        }

        return userList; // 如果发生异常，默认返回 false
    }

    public static boolean loginVerify(String username,String password){
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        try {
            conn = DbUtil.getConnection();
            String sql = "SELECT name,password FROM User WHERE name=?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, username);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedHashedPassword = rs.getString("password");

                return BCryptUtil.match(password, storedHashedPassword);
            }
            }catch(SQLException e){
                LOGGER.log(Level.SEVERE,"数据库异常： "+ e.getMessage(),e);
            }finally{
                DbUtil.release(conn, pstmt, rs);
            }
//        密码验证失败
        return false;
    }

    // 删除用户
    public static Result rmUser(String username) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int code=0;//状态码，默认为失败
        ResultSet rs=null;

        try {
            conn = DbUtil.getConnection();
            String sql1 = "SELECT Access FROM User WHERE name=?";
            pstmt = conn.prepareStatement(sql1);
            pstmt.setString(1, username);

            rs=pstmt.executeQuery();
            if (rs.next()) {
                if (rs.getInt("access") == 0) {
                    return new Result(code, "权限不够，无法删除该用户");
                }else {
                    String sql2 = "DELETE FROM User WHERE name=?";
                    pstmt = conn.prepareStatement(sql2);
                    pstmt.setString(1, username);

                    int affectedRows = pstmt.executeUpdate();
                    if (affectedRows > 0) {
                        code=1;
                        return new Result(code,String.format("删除成功,用户%s被删除",username));
                    } else {
//                System.out.println("未找到要删除的用户");
                        return new Result(code,"未找到要删除的用户");
                    }
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,"数据库异常： "+ e.getMessage(),e);
        } finally {
            DbUtil.release(conn, pstmt, rs);
        }
        return new Result(code,"异常");
    }

    // 将用户更改为管理员
    public static boolean setAdmin(String username) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int newAccess=1;

        try {
            conn = DbUtil.getConnection();
            String sql = "UPDATE User SET  access=? WHERE name=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, newAccess);
            pstmt.setString(2, username);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("更新成功");
                return true;
            } else {
                System.out.println("未找到要更新的用户");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,"数据库异常： "+ e.getMessage(),e);
        } finally {
            DbUtil.release(conn, pstmt, null);
        }
        return false;
    }

//    撤销管理员
    public static boolean unsetAdmin(String username) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int newAccess=2;
        boolean code=false;//状态码，默认失败

        try {
            conn = DbUtil.getConnection();
            String sql = "UPDATE User SET  access=? WHERE name=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, newAccess);
            pstmt.setString(2, username);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                code=true;
            }
            return code;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,"数据库异常： "+ e.getMessage(),e);
        } finally {
            DbUtil.release(conn, pstmt, null);
        }
        return code;
    }

    public static Result resetUserEmail(String username,String newemail) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int code=0;//状态码，默认失败
        String result="修改失败";

        try {
            conn = DbUtil.getConnection();
            String sql = "UPDATE User SET  email=? WHERE name=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newemail);
            pstmt.setString(2, username);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                code=1;
                result="修改成功";
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,"数据库异常： "+ e.getMessage(),e);
        } finally {
            DbUtil.release(conn, pstmt, null);
        }
        return new Result(code,result);
    }

//    检测用户access
    public static int verifyAdmin(String Username){
        Connection conn=null;
        PreparedStatement pstmt=null;
        int access=0;
        ResultSet rs;

        try{
            conn=DbUtil.getConnection();
            String sql="SELECT ACCESS FROM User where name=?";
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1,Username);

            rs=pstmt.executeQuery();
            if(rs.next()){
                access=rs.getInt("access");
                return access;
            }

        }catch (SQLException e) {
            LOGGER.log(Level.SEVERE,"数据库异常： "+ e.getMessage(),e);
        } finally {
            DbUtil.release(conn, pstmt, null);
        }
        return access;
    }

    public static User getUserInfo(String Username){
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs;

        User user=new User();
        String email=null;
        int access=0;
        try{
            conn=DbUtil.getConnection();
            String sql="SELECT email,access FROM User where name=?";
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1,Username);

            rs=pstmt.executeQuery();
            if(rs.next()){
                access=rs.getInt(2);
                email=rs.getString(1);
                if(Objects.equals(email, ""))email= String.valueOf(0);
            }

            user.setAccess(access);
            user.setEmail(email);
        }catch (SQLException e) {
            LOGGER.log(Level.SEVERE,"数据库异常： "+ e.getMessage(),e);
        } finally {
            DbUtil.release(conn, pstmt, null);
        }
        return user;
    }
    public static void main(String[] args){
//        System.out.println(getAllUser());
        System.out.println(getUserInfo("admin"));
    }
}