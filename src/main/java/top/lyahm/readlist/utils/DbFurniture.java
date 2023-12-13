package top.lyahm.readlist.utils;

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
            } else {
                new Result(code, "插入失败，受影响行数为0");
            }
            return;
        }catch (SQLException e) {
            // 记录数据库异常信息
            LOGGER.log(Level.SEVERE,"数据库异常： "+ e.getMessage(),e);
        }finally {
            DbUtil.release(conn, pstmt, null);
        }
        new Result(code, "没有成功");
    }

//取出gptMSG信息
//    public static Result getGMSG(){
////        更新GPT数据库信息
//        DbData.storageData();
//        int code=0;
//        Connection conn=null;
//        PreparedStatement pstmt=null;
//        ResultSet rs=null;
//
//        String message=null;
////        预编译sql语句
//        try{
//            conn = DbUtil.getConnection();
//            String sql="SELECT MSG FROM gptMSG ORDER BY MID DESC LIMIT 1";
//            pstmt=conn.prepareStatement(sql);
//
//            rs= pstmt.executeQuery();
//
//            if(rs.next()){
//                message=rs.getString(1);
//                return new Result(code,message);
//            }
//        }catch (SQLException e) {
//            // 记录数据库异常信息
//            LOGGER.log(Level.SEVERE,"数据库异常： "+ e.getMessage(),e);
//        }finally {
//            DbUtil.release(conn, pstmt, null);
//        }
//        return new Result(code,message);
//    }

    public static ArrayList<FurnitureData> getFurnitureStatue(int Fid){
        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs;
        ArrayList<FurnitureData> ACD=new ArrayList<>();

        int Statue=0;
//        预编译sql语句
        try{
            conn = DbUtil.getConnection();
            String sql="SELECT Statue from Furniture where Fid=?";

            pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1,Fid);
            rs= pstmt.executeQuery();
            if(rs.next()){
                Statue=rs.getInt(1);
            }
            FurnitureData Data=new FurnitureData();
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

    private static String getRoomNameByRoomId(Connection conn, int RoomId) throws SQLException {
        String RoomName = null;
        String sql = "SELECT RoomName FROM Room WHERE RoomId=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, RoomId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    RoomName = rs.getString("RoomName");
                }
            }
        }
        return RoomName;
    }

    private static String getFname(Connection conn, int Fid) throws SQLException {
        String Fname = null;
        String sql = "SELECT Fname FROM Furniture WHERE Fid=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, Fid);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Fname = rs.getString("Fname");
                }
            }
        }
        return Fname;
    }
    public static ArrayList<airConditionerData> getAirData() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<airConditionerData> ACD = new ArrayList<>();

        try {
            conn = DbUtil.getConnection();
            for (int Fid = 1; Fid <= 3; Fid++) {
                String sql = "SELECT MaxTemp, MinTemp, Statue, WSpeed, Power, RoomId FROM airConditioner WHERE Fid=?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, Fid);
                rs = pstmt.executeQuery();

                while (rs.next()) {
                    int MaxTemp = rs.getInt("MaxTemp");
                    int MinTemp = rs.getInt("MinTemp");
                    int Statue = rs.getInt("Statue");
                    int WSpeed = rs.getInt("WSpeed");
                    int Power = rs.getInt("Power");
                    int RoomId = rs.getInt("RoomId");
                    String Fname=getFname(conn,Fid);
                    String RoomName = getRoomNameByRoomId(conn, RoomId);

                    airConditionerData Data = new airConditionerData();
                    Data.setMaxTemp(MaxTemp);
                    Data.setMinTemp(MinTemp);
                    Data.setStatue(Statue);
                    Data.setWSpeed(WSpeed);
                    Data.setPower(Power);
                    Data.setRoomName(RoomName);
                    Data.setFname(Fname);
                    ACD.add(Data);
                }
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "数据库异常： " + e.getMessage(), e);
        } finally {
            DbUtil.release(conn, pstmt, rs);
        }
        return ACD;
    }


    //    删除家具，只删除Furniture表
    public static Result rmFurniture(int Fid){
        Connection conn=null;
        PreparedStatement pstmt=null;
        int code=0;
        String result="删除失败";
//        预编译sql语句
        try{
            conn = DbUtil.getConnection();
            String sql="DELETE FROM Furniture WHERE Fid=?";

            pstmt=conn.prepareStatement(sql);
            pstmt.setInt(1,Fid);

            int affectedRows = pstmt.executeUpdate();
            if (affectedRows > 0) {
                code=1;
                result="修改成功";
            }

        }catch (SQLException e) {
            // 记录数据库异常信息
            LOGGER.log(Level.SEVERE,"数据库异常： "+ e.getMessage(),e);
        }finally {
            DbUtil.release(conn, pstmt, null);
        }
        return new Result(code,result);
    }

    public static ArrayList<HumidifierData> getHumiDifierData() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<HumidifierData> ACD = new ArrayList<>();

        try {
            conn = DbUtil.getConnection();
            for (int Fid = 10; Fid <= 11; Fid++) {
                String sql = "SELECT Humi, Threshold, Power, Statue, RoomId FROM Humidifier WHERE Fid=?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, Fid);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    int Humi = rs.getInt("Humi");
                    int Threshold = rs.getInt("Threshold");
                    int Power = rs.getInt("Power");
                    int Statue = rs.getInt("Statue");
                    int RoomId = rs.getInt("RoomId");
                    String Fname=getFname(conn,Fid);

                    HumidifierData Data = new HumidifierData();
                    Data.setHumi(Humi);
                    Data.setThreshold(Threshold);
                    Data.setPower(Power);
                    Data.setStatue(Statue);
                    Data.setFname(Fname);

                    String RoomName = getRoomNameByRoomId(conn, RoomId);
                    Data.setRoomName(RoomName);

                    ACD.add(Data);
                }
            }
        } catch (SQLException e) {
            // 记录数据库异常信息
            LOGGER.log(Level.SEVERE, "数据库异常： " + e.getMessage(), e);
        } finally {
            DbUtil.release(conn, pstmt, rs);
        }
        return ACD;
    }


    public static ArrayList<CurtainData> getCurtainData() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<CurtainData> ACD = new ArrayList<>();

        try {
            conn = DbUtil.getConnection();
            for (int Fid = 6; Fid <= 9; Fid++) {
                String sql = "SELECT Threshold, Statue, RoomId FROM Curtain WHERE Fid=?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, Fid);
                rs = pstmt.executeQuery();

                if (rs.next()) {
                    int Threshold = rs.getInt("Threshold");
                    int Statue = rs.getInt("Statue");
                    int RoomId = rs.getInt("RoomId");
                    String Fname=getFname(conn,Fid);

                    CurtainData Data = new CurtainData();
                    String RoomName = getRoomNameByRoomId(conn, RoomId);
                    Data.setThreshold(Threshold);
                    Data.setStatue(Statue);
                    Data.setRoomName(RoomName);
                    Data.setFname(Fname);
                    ACD.add(Data);
                }
            }
        } catch (SQLException e) {
            // 记录数据库异常信息
            LOGGER.log(Level.SEVERE, "数据库异常： " + e.getMessage(), e);
        } finally {
            DbUtil.release(conn, pstmt, rs);
        }
        return ACD;
    }


    public static ArrayList<RefrigeratorData> getRefrigeratorData() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        ArrayList<RefrigeratorData> ACD = new ArrayList<>();

        try {
            conn = DbUtil.getConnection();
            int Fid = 5; // Set FId to 5
            String sql = "SELECT RefrigerationThreshold, FrozenThreshold, Power, Statue, RoomId FROM Refrigerator WHERE Fid=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, Fid);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                int RefrigeratorThreshold = rs.getInt("RefrigerationThreshold");
                int FrozenThreshold = rs.getInt("FrozenThreshold");
                int Power = rs.getInt("Power");
                int Statue = rs.getInt("Statue");
                int RoomId = rs.getInt("RoomId");
                String Fname=getFname(conn,Fid);

                RefrigeratorData Data = new RefrigeratorData();
                String RoomName = getRoomNameByRoomId(conn, RoomId);
                Data.setRefrigerationThreshold(RefrigeratorThreshold);
                Data.setRoomName(RoomName);
                Data.setFrozenThreshold(FrozenThreshold);
                Data.setPower(Power);
                Data.setStatue(Statue);
                Data.setFname(Fname);
                ACD.add(Data);
            }
        } catch (SQLException e) {
            // 记录数据库异常信息
            LOGGER.log(Level.SEVERE, "数据库异常： " + e.getMessage(), e);
        } finally {
            DbUtil.release(conn, pstmt, rs);
        }
        return ACD;
    }


    //    获取家具状态
    public static int[] getFurnitureStatus(){
        int [] Results=new int[3];

        Connection conn=null;
        PreparedStatement pstmt=null;
        ResultSet rs;

        int Statue;
//        预编译sql语句
        try{
            conn = DbUtil.getConnection();
            String sql="SELECT Statue from Furniture where Fid=?";

            for(int i=0;i<12;i++){
                if(i==4){
                    continue;
                }
                pstmt=conn.prepareStatement(sql);
                pstmt.setInt(1, i);
                rs= pstmt.executeQuery();
                if(rs.next()){
                    Statue=rs.getInt(1);
                    if(Statue==0){
                        Results[0]++;
                    } else if (Statue==1) {
                        Results[1]++;
                    }else {
                        Results[2]++;
                    }
                }
            }

        }catch (SQLException e) {
            // 记录数据库异常信息
            LOGGER.log(Level.SEVERE,"数据库异常： "+ e.getMessage(),e);
        }finally {
            DbUtil.release(conn, pstmt, null);
        }
        return Results;
    }


    public static Result moveFurniture(int FId, int newRoomId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int code=0;
        String result="没有移动成功";

        try {
            conn = DbUtil.getConnection();
            String sql = "UPDATE Furniture SET RoomId = ? WHERE Fid = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, newRoomId);
            pstmt.setInt(2, FId);

            int rowsAffected = pstmt.executeUpdate();
            if(rowsAffected>0){
                code=1;
                result="移动成功";
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "数据库异常： " + e.getMessage(), e);
        } finally {
            DbUtil.release(conn, pstmt, null);
        }

        return new Result(code,result);
    }

    public static void main(String[] args){
//        moveFurniture(5,5);
        System.out.println(getHumiDifierData());
    }
}
