package top.lyahm.readlist.utils;

import top.lyahm.readlist.vo.Result;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbFurniture {
    private static final Logger LOGGER = Logger.getLogger(DbUser.class.getName());
//    更新gptMSG数据
    public static Result updateGMSG(String MSG){
        int code=0;
        Connection conn=null;
        PreparedStatement pstmt=null;

//        预编译sql语句
        try{
            conn = DbUtil.getConnection();
            String sql="INSERT INTO gptMSG(MSG) VALUES (?)";
            pstmt=conn.prepareStatement(sql);
            pstmt.setString(1,MSG);
            int affectedRows = pstmt.executeUpdate();
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

//取出gptMSG信息
    public static Result getGMSG(){
        int code=0;
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;

        String message=null;
//        预编译sql语句
        try{
            conn = DbUtil.getConnection();
            String sql="SELECT MSG FROM gptMSG ORDER BY MID DESC LIMIT 1";
            pstmt=conn.prepareStatement(sql);

            rs= pstmt.executeQuery();

            if(rs.next()){
                message=rs.getString(1);
                return new Result(code,message);
            }
        }catch (SQLException e) {
            // 记录数据库异常信息
            LOGGER.log(Level.SEVERE,"数据库异常： "+ e.getMessage(),e);
        }finally {
            DbUtil.release(conn, pstmt, null);
        }
        return new Result(code,message);
    }

//    public static void main(String[] args){
//        System.out.println(getGMSG());
//    }
}
