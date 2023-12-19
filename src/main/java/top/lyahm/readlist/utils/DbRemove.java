package top.lyahm.readlist.utils;

import top.lyahm.readlist.vo.Result;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbRemove {
    private static final Logger LOGGER = Logger.getLogger(DbUser.class.getName());
    public static Result rmAirConditioner(int Fid) {
        Connection conn = null;
        PreparedStatement pstmtDelete = null;
        int code = 0;
        String result = "删除失败";

        try {
            conn = DbUtil.getConnection();

            // 执行删除操作
            String deleteSql = "DELETE FROM airConditioner WHERE Fid=?";
            pstmtDelete = conn.prepareStatement(deleteSql);
            pstmtDelete.setInt(1, Fid);

            int affectedRows = pstmtDelete.executeUpdate();
            if (affectedRows > 0) {
                code = 1;
                result = "删除成功";
            }

        } catch (SQLException e) {
            // 记录数据库异常信息
            LOGGER.log(Level.SEVERE, "数据库异常： " + e.getMessage(), e);
        } finally {
            DbUtil.release(conn, null, null);
            DbUtil.release(null, pstmtDelete, null);
        }

        return new Result(code, result);
    }

    public static Result rmHumidifier(int Fid) {
        Connection conn = null;
        PreparedStatement pstmtDelete = null;
        int code = 0;
        String result = "删除失败";

        try {
            conn = DbUtil.getConnection();

            // 执行删除操作
            String deleteSql = "DELETE FROM Humidifier WHERE Fid=?";
            pstmtDelete = conn.prepareStatement(deleteSql);
            pstmtDelete.setInt(1, Fid);

            int affectedRows = pstmtDelete.executeUpdate();
            if (affectedRows > 0) {
                code = 1;
                result = "删除成功";
            }

        } catch (SQLException e) {
            // 记录数据库异常信息
            LOGGER.log(Level.SEVERE, "数据库异常： " + e.getMessage(), e);
        } finally {
            DbUtil.release(conn, null, null);
            DbUtil.release(null, pstmtDelete, null);
        }

        return new Result(code, result);
    }

    public static Result rmCurtain(int Fid) {
        Connection conn = null;
        PreparedStatement pstmtDelete = null;
        int code = 0;
        String result = "删除失败";

        try {
            conn = DbUtil.getConnection();

            // 执行删除操作
            String deleteSql = "DELETE FROM Curtain WHERE Fid=?";
            pstmtDelete = conn.prepareStatement(deleteSql);
            pstmtDelete.setInt(1, Fid);

            int affectedRows = pstmtDelete.executeUpdate();
            if (affectedRows > 0) {
                code = 1;
                result = "删除成功";
            }

        } catch (SQLException e) {
            // 记录数据库异常信息
            LOGGER.log(Level.SEVERE, "数据库异常： " + e.getMessage(), e);
        } finally {
            DbUtil.release(conn, null, null);
            DbUtil.release(null, pstmtDelete, null);
        }

        return new Result(code, result);
    }

    public static Result rmRefrigerator(int Fid) {
        Connection conn = null;
        PreparedStatement pstmtDelete = null;
        int code = 0;
        String result = "删除失败";

        try {
            conn = DbUtil.getConnection();

            // 执行删除操作
            String deleteSql = "DELETE FROM Refrigerator WHERE Fid=?";
            pstmtDelete = conn.prepareStatement(deleteSql);
            pstmtDelete.setInt(1, Fid);

            int affectedRows = pstmtDelete.executeUpdate();
            if (affectedRows > 0) {
                code = 1;
                result = "删除成功";
            }

        } catch (SQLException e) {
            // 记录数据库异常信息
            LOGGER.log(Level.SEVERE, "数据库异常： " + e.getMessage(), e);
        } finally {
            DbUtil.release(conn, null, null);
            DbUtil.release(null, pstmtDelete, null);
        }

        return new Result(code, result);
    }


}
