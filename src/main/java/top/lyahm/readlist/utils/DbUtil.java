package top.lyahm.readlist.utils;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DbUtil {

    private static final Logger LOGGER = Logger.getLogger(DbUtil.class.getName());
    private static String username=null;
    private static String password=null;
    private static String url=null;

    static {
        try {
            String path = "Db.properties";
            InputStream is = DbUtil.class.getClassLoader().getResourceAsStream(path);

            if (is == null) {
                throw new RuntimeException("Could not find resource: " + path);
            }

            Properties properties = new Properties();
            properties.load(is);

            // 获取Db.property信息
            String driver = properties.getProperty("driver");
            url = properties.getProperty("url");
            username = properties.getProperty("username");
            password = properties.getProperty("password");

            // 加载驱动
            Class.forName(driver);
        } catch (IOException | ClassNotFoundException e) {
            LOGGER.log(Level.SEVERE,"数据库异常： "+ e.getMessage(),e);
        }
    }

    // 获取连接
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, username, password);
    }

    // 释放连接
    public static void release(Connection conn, Statement stmt, ResultSet rs) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE,"数据库异常： "+ e.getMessage(),e);
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE,"数据库异常： "+ e.getMessage(),e);
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                LOGGER.log(Level.SEVERE,"数据库异常： "+ e.getMessage(),e);
            }
        }
    }

}

