package top.lyahm.readlist.utils;

import top.lyahm.readlist.vo.Result;
import top.lyahm.readlist.vo.User;

import java.sql.*;
import java.util.ArrayList;
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
                if(rs.getInt(3)==0){
                    user.setAdmin(true);
                }else{
                    user.setAdmin(false);
                }
//                如果 email 的值不为null，则设置haveEmail为true
                if(rs.getString(2)!=null){
                    user.setHaveEmail(true);
                }else{
                    user.setHaveEmail(false);
                }
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
    public static void deleteUser(String username) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DbUtil.getConnection();
            String sql = "DELETE FROM User WHERE name=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("删除成功");
            } else {
                System.out.println("未找到要删除的用户");
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,"数据库异常： "+ e.getMessage(),e);
        } finally {
            DbUtil.release(conn, pstmt, null);
        }
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

    public static void main(String[] args){
//        System.out.println(getAllUser());
        System.out.println(registerCheck("admin"));
    }
}