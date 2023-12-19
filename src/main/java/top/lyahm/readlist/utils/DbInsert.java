package top.lyahm.readlist.utils;

import top.lyahm.readlist.vo.Result;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbInsert {
    private static final Logger LOGGER = Logger.getLogger(DbUser.class.getName());
    public static Result insertAirConditioner(int generatedFid, int RoomId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int code = 0;
        String result = "插入失败";

        try {
            conn = DbUtil.getConnection();
            String sql = "INSERT INTO airConditioner (Fid, MaxTemp, MinTemp, Statue, WSpeed, Power,RoomId) VALUES (?, 0, 0, 0, 0, 0,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, generatedFid);
            pstmt.setInt(2,RoomId);
            int rs = pstmt.executeUpdate();

            if (rs > 0) {
                result = "插入成功";
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "数据库异常： " + e.getMessage(), e);
        } finally {
            DbUtil.release(conn, pstmt, null);
        }

        return new Result(code, result);
    }

    public static Result insertRefrigerator(int generatedFid,int RoomId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int code = 0;
        String result = "插入失败";

        try {
            conn = DbUtil.getConnection();
            String sql = "INSERT INTO Refrigerator (Fid, RefrigerationThreshold, FrozenThreshold, Power, Statue,RoomId) VALUES (?, 0, 0, 0,0, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, generatedFid);
            pstmt.setInt(2,RoomId);
            int rs = pstmt.executeUpdate();

            if (rs > 0) {
                result = "插入成功";
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "数据库异常： " + e.getMessage(), e);
        } finally {
            DbUtil.release(conn, pstmt, null);
        }

        return new Result(code, result);
    }

    public static Result insertCurtain(int generatedFid,int RoomId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int code = 0;
        String result = "插入失败";

        try {
            conn = DbUtil.getConnection();
            String sql = "INSERT INTO Curtain (Fid, Threshold,RoomId) VALUES (?, 0,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, generatedFid);
            pstmt.setInt(2,RoomId);
            int rs = pstmt.executeUpdate();

            if (rs > 0) {
                result = "插入成功";
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "数据库异常： " + e.getMessage(), e);
        } finally {
            DbUtil.release(conn, pstmt, null);
        }

        return new Result(code, result);
    }

    public static Result insertHumidifier(int generatedFid,int RoomId) {
        Connection conn = null;
        PreparedStatement pstmt = null;
        int code = 0;
        String result = "插入失败";

        try {
            conn = DbUtil.getConnection();
            String sql = "INSERT INTO Humidifier (Fid, Humi, Threshold, Power, Statue,RoomId) VALUES (?, 0, 0, 0, 0,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, generatedFid);
            pstmt.setInt(2,RoomId);
            int rs = pstmt.executeUpdate();

            if (rs > 0) {
                result = "插入成功";
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "数据库异常： " + e.getMessage(), e);
        } finally {
            DbUtil.release(conn, pstmt, null);
        }

        return new Result(code, result);
    }
}
