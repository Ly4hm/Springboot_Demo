package top.lyahm.readlist.utils;

import jdk.jfr.Threshold;
import top.lyahm.readlist.vo.FurnitureData;
import top.lyahm.readlist.vo.Result;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbUpdateFurniture {
    private static final Logger LOGGER = Logger.getLogger(DbUser.class.getName());

//    修改家具状态
    public static Result updateFurnitureStatue(int Fid, int newStatue) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int code=0;
        String result=null;

        ArrayList<FurnitureData> OLDStatue =DbFurniture.getFurnitureStatue(Fid);
        if(OLDStatue.get(0).getStatue()==newStatue){
            result="状态未改变，看看是不是输错了";
            return new Result(code,result);
        }

        try {
            conn = DbUtil.getConnection();
            String sql = "UPDATE Furniture SET Statue = ? WHERE Fid = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, newStatue);
            pstmt.setInt(2, Fid);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                code=1;
                result="Refrigerator Statue updated successfully.";
            } else {
                result="No rows were updated.";
            }
        } catch (SQLException e) {
            // 处理数据库异常
            LOGGER.log(Level.SEVERE,"数据库异常： "+ e.getMessage(),e);
        } finally {
            DbUtil.release(conn, pstmt, null);
        }
        return new Result(code,result);
    }


    public static Result updateFurnitureName(int Fid, String newFname) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int code=0;
        String result=null;

        try {
            conn = DbUtil.getConnection();
            String sql = "UPDATE Furniture SET Fname = ? WHERE Fid = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, newFname);
            pstmt.setInt(2, Fid);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                code=1;
                result="Furniture name updated successfully.";
            } else {
                result="No rows were updated.";
            }
        } catch (SQLException e) {
            // 处理数据库异常
            LOGGER.log(Level.SEVERE,"数据库异常： "+ e.getMessage(),e);
        } finally {
            DbUtil.release(conn, pstmt, null);
        }
        return new Result(code,result);
    }

//    修改空调制热阈值
    public static Result updateAirconditionerMinTemp(int Fid,int newMinTemp) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int code=0;
        String result=null;

        try {
            conn = DbUtil.getConnection();
            String sql = "UPDATE airConditioner SET MinTemp = ? WHERE Fid = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, newMinTemp);
            pstmt.setInt(2, Fid);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                code=1;
                result="Air-conditioner min-temp updated successfully.";
            } else {
                result="No rows were updated.";
            }
        } catch (SQLException e) {
            // 处理数据库异常
            LOGGER.log(Level.SEVERE,"数据库异常： "+ e.getMessage(),e);
        } finally {
            DbUtil.release(conn, pstmt, null);
        }
        return new Result(code,result);
    }

//    修改空调制冷阈值
    public static Result updateAirconditionerMaxTemp(int Fid,int newMaxTemp) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int code=0;
        String result=null;

        try {
            conn = DbUtil.getConnection();
            String sql = "UPDATE airConditioner SET MaxTemp = ? WHERE Fid = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, newMaxTemp);
            pstmt.setInt(2, Fid);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                code=1;
                result="Air-conditioner max-temp updated successfully.";
            } else {
                result="No rows were updated.";
            }
        } catch (SQLException e) {
            // 处理数据库异常
            LOGGER.log(Level.SEVERE,"数据库异常： "+ e.getMessage(),e);
        } finally {
            DbUtil.release(conn, pstmt, null);
        }
        return new Result(code,result);
    }

//    修改制冷器阈值
    public static Result updateHumidifierThreshold(int Fid,int newThreshold) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int code=0;
        String result=null;

        try {
            conn = DbUtil.getConnection();
            String sql = "UPDATE Humidifier SET Threshold = ? WHERE Fid = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, newThreshold);
            pstmt.setInt(2, Fid);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                code=1;
                result="Humidifier's threshold updated successfully.";
            } else {
                result="No rows were updated.";
            }
        } catch (SQLException e) {
            // 处理数据库异常
            LOGGER.log(Level.SEVERE,"数据库异常： "+ e.getMessage(),e);
        } finally {
            DbUtil.release(conn, pstmt, null);
        }
        return new Result(code,result);
    }


//    修改窗帘阈值
    public static Result updateCurtainThreshold(int Fid,int newThreshold) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int code=0;
        String result=null;

        try {
            conn = DbUtil.getConnection();
            String sql = "UPDATE Curtain SET Threshold = ? WHERE Fid = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, newThreshold);
            pstmt.setInt(2, Fid);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                code=1;
                result="Curtain's threshold updated successfully.";
            } else {
                result="No rows were updated.";
            }
        } catch (SQLException e) {
            // 处理数据库异常
            LOGGER.log(Level.SEVERE,"数据库异常： "+ e.getMessage(),e);
        } finally {
            DbUtil.release(conn, pstmt, null);
        }
        return new Result(code,result);
    }

//    修改冰箱冷藏阈值
    public static Result updateRefrigeratorRefrigerationThreshold(int Fid,int newRefrigerationThreshold) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int code=0;
        String result=null;

        try {
            conn = DbUtil.getConnection();
            String sql = "UPDATE Refrigerator SET RefrigerationThreshold = ? WHERE Fid = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, newRefrigerationThreshold);
            pstmt.setInt(2, Fid);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                code=1;
                result="Refrigerator's refrigeration-threshold updated successfully.";
            } else {
                result="No rows were updated.";
            }
        } catch (SQLException e) {
            // 处理数据库异常
            LOGGER.log(Level.SEVERE,"数据库异常： "+ e.getMessage(),e);
        } finally {
            DbUtil.release(conn, pstmt, null);
        }
        return new Result(code,result);
    }

//    修改冰箱冷冻阈值
    public static Result updateRefrigeratorFrozenThreshold(int Fid,int newFrozenThreshold) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int code=0;
        String result=null;

        try {
            conn = DbUtil.getConnection();
            String sql = "UPDATE Refrigerator SET FrozenThreshold = ? WHERE Fid = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, newFrozenThreshold);
            pstmt.setInt(2, Fid);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                code=1;
                result="Refrigerator's frozen-threshold updated successfully.";
            } else {
                result="No rows were updated.";
            }
        } catch (SQLException e) {
            // 处理数据库异常
            LOGGER.log(Level.SEVERE,"数据库异常： "+ e.getMessage(),e);
        } finally {
            DbUtil.release(conn, pstmt, null);
        }
        return new Result(code,result);
    }
    public static void main(String[] args){
        System.out.println(updateFurnitureStatue(3,0));
    }
}
