package top.lyahm.readlist.utils;

import lombok.Data;
import top.lyahm.readlist.vo.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbFurniture {
    private static final Logger LOGGER = Logger.getLogger(DbUser.class.getName());
//    更新gptMSG数据
    public static void updateGMSG(String MSG){
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
                new Result(code, message);
                return;
            } else {
                new Result(code, "插入失败，受影响行数为0");
                return;
            }
        }catch (SQLException e) {
            // 记录数据库异常信息
            LOGGER.log(Level.SEVERE,"数据库异常： "+ e.getMessage(),e);
        }finally {
            DbUtil.release(conn, pstmt, null);
        }
        new Result(code, "没有成功");
    }

//取出gptMSG信息
    public static Result getGMSG(){
//        更新GPT数据库信息
        DbData.storageData();
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

    public static ArrayList<airConditionerData> getAirData(int FId){
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        ArrayList<airConditionerData> ACD=new ArrayList<>();

        int MaxTemp=0;
        int MinTemp=0;
        String Statue=null;
        int WSpeed=0;
        int Power=0;
//        预编译sql语句
        try{
            conn = DbUtil.getConnection();
            String sql="SELECT MaxTemp,MinTemp,Statue,WSpeed,Power from airConditioner where Fid=?";

            pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1,FId);
            rs= pstmt.executeQuery();
            if(rs.next()){
                MaxTemp=rs.getInt(1);
                MinTemp=rs.getInt(2);
                Statue=rs.getString(3);
                WSpeed=rs.getInt(4);
                Power=rs.getInt(5);
            }
            airConditionerData Data=new airConditionerData();
            Data.setMaxTemp(MaxTemp);
            Data.setMinTemp(MinTemp);
            Data.setStatue(Statue);
            Data.setWSpeed(WSpeed);
            Data.setPower(Power);
            ACD.add(Data);

        }catch (SQLException e) {
            // 记录数据库异常信息
            LOGGER.log(Level.SEVERE,"数据库异常： "+ e.getMessage(),e);
        }finally {
            DbUtil.release(conn, pstmt, null);
        }
        return ACD;
    }

    public static ArrayList<HumidifierData> getHumiDifierData(int FId){
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        ArrayList<HumidifierData> ACD=new ArrayList<>();

        int Humi=0;
        int Threshold=0;
        int Power=0;
        String Statue=null;
//        预编译sql语句
        try{
            conn = DbUtil.getConnection();
            String sql="SELECT Humi,Threshold,Power,Statue from Humidifier where Fid=?";

            pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1,FId);
            rs= pstmt.executeQuery();
            if(rs.next()){
                Humi=rs.getInt(1);
                Threshold=rs.getInt(2);
                Power=rs.getInt(3);
                Statue=rs.getString(4);
            }
            HumidifierData Data=new HumidifierData();
            Data.setHumi(Humi);
            Data.setThreshold(Threshold);
            Data.setPower(Power);
            Data.setStatue(Statue);
            ACD.add(Data);

        }catch (SQLException e) {
            // 记录数据库异常信息
            LOGGER.log(Level.SEVERE,"数据库异常： "+ e.getMessage(),e);
        }finally {
            DbUtil.release(conn, pstmt, null);
        }
        return ACD;
    }

    public static ArrayList<CurtainData> getCurtainData(int FId){
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        ArrayList<CurtainData> ACD=new ArrayList<>();

        int Threshold=0;
        String Statue=null;
//        预编译sql语句
        try{
            conn = DbUtil.getConnection();
            String sql="SELECT Threshold,Statue from Curtain where Fid=?";

            pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1,FId);
            rs= pstmt.executeQuery();
            if(rs.next()){
                Threshold=rs.getInt(1);
                Statue=rs.getString(2);
            }
            CurtainData Data=new CurtainData();
            Data.setThreshold(Threshold);
            Data.setStatue(Statue);
            ACD.add(Data);

        }catch (SQLException e) {
            // 记录数据库异常信息
            LOGGER.log(Level.SEVERE,"数据库异常： "+ e.getMessage(),e);
        }finally {
            DbUtil.release(conn, pstmt, null);
        }
        return ACD;
    }

    public static ArrayList<RefrigeratorData> getRefrigeratorData(int FId){
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs=null;
        ArrayList<RefrigeratorData> ACD=new ArrayList<>();

        int RefrigeratorThreshold=0;
        int FrozenThreshold=0;
        int Power=0;
        String Statue=null;
//        预编译sql语句
        try{
            conn = DbUtil.getConnection();
            String sql="SELECT RefrigerationThreshold,FrozenThreshold,Power,Statue from Refrigerator where Fid=?";

            pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1,FId);
            rs= pstmt.executeQuery();
            if(rs.next()){
                RefrigeratorThreshold=rs.getInt(1);
                FrozenThreshold=rs.getInt(2);
                Power=rs.getInt(3);
                Statue=rs.getString(4);
            }
            RefrigeratorData Data=new RefrigeratorData();
            Data.setRefrigerationThreshold(RefrigeratorThreshold);
            Data.setFrozenThreshold(FrozenThreshold);
            Data.setPower(Power);
            Data.setStatue(Statue);
            ACD.add(Data);

        }catch (SQLException e) {
            // 记录数据库异常信息
            LOGGER.log(Level.SEVERE,"数据库异常： "+ e.getMessage(),e);
        }finally {
            DbUtil.release(conn, pstmt, null);
        }
        return ACD;
    }

//    获取家具状态
//    public static int[] getFurnitureStatus(){
//        int [] Results=new int[3];
//
//        Connection conn=null;
//        PreparedStatement pstmt=null;
//        ResultSet rs=null;
//
//        String Statue=null;
////        预编译sql语句
//        try{
//            conn = DbUtil.getConnection();
//            String sql="SELECT RefrigerationThreshold,FrozenThreshold,Power,Statue from Refrigerator where Fid=?";
//
//            pstmt=conn.prepareStatement(sql);
//            pstmt.setInt(1,FId);
//            rs= pstmt.executeQuery();
//            if(rs.next()){
//                Statue=rs.getString(4);
//            }
//
//        }catch (SQLException e) {
//            // 记录数据库异常信息
//            LOGGER.log(Level.SEVERE,"数据库异常： "+ e.getMessage(),e);
//        }finally {
//            DbUtil.release(conn, pstmt, null);
//        }
//        return Results;
//    }

    public static void main(String[] args){
        System.out.println(getCurtainData(9));
    }
}
