package top.lyahm.readlist.utils;

import java.sql.*;

public class DbUser{
//    执行插入操作，用jbcrypt加密
    public static void doRegiser(int id,String name,int access,String password){
        Connection conn=null;
        PreparedStatement pstmt=null;
        String encodepassword=null;

        encodepassword=BCryptUtil.encode(password);

//        预编译sql语句
        try{
            conn = DbUtil.getConnection();
            String sql="INSERT INTO user(ID,NAME,ACCESS,PASSWORD) VALUES (?,?,?,?)";
            pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1,id);
            pstmt.setString(2,name);
            pstmt.setInt(3,access);
            pstmt.setString(4,encodepassword);

            int affectedRows=pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("插入成功");
            }
        }catch (SQLException e){
                e.printStackTrace();
        }finally{
            DbUtil.release(conn,pstmt,null);
        }
    }

    public static boolean registerCheck(String username) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DbUtil.getConnection();
            String sql = "SELECT COUNT(*) FROM user WHERE name=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // 如果 count 大于 0，表示用户名已存在
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.release(conn, pstmt, rs);
        }

        return false; // 如果发生异常，默认返回 false
    }

    public static boolean loginVerify(String username,String password){
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        try {
            conn = DbUtil.getConnection();
            String sql = "SELECT name,password,salt FROM User WHERE name=?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, username);

            rs = pstmt.executeQuery();

            if (rs.next()) {
                String storedHashedPassword = rs.getString("password");

                return BCryptUtil.match(password, storedHashedPassword);
            }
            }catch(SQLException e){
                e.printStackTrace();
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
            String sql = "DELETE FROM user WHERE name=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, username);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("删除成功");
            } else {
                System.out.println("未找到要删除的用户");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.release(conn, pstmt, null);
        }
    }

    // 更新用户信息
    public static void updateUser(String username, String newName, int newAccess) {
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DbUtil.getConnection();
            String sql = "UPDATE user SET name=?, access=? WHERE name=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newName);
            pstmt.setInt(2, newAccess);
            pstmt.setString(3, username);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("更新成功");
            } else {
                System.out.println("未找到要更新的用户");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            DbUtil.release(conn, pstmt, null);
        }
    }
}