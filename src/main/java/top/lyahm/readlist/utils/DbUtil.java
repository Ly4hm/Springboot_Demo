package top.lyahm.readlist.utils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbUtil {
    private static final String URL = "jdbc:mariadb://47.97.16.22:3306/CInfo";
    private static final String USER = "root";
    private static final String  PASSWORD = "python666";
    private static Connection conn = null;

    public DbUtil() throws Exception{
        Class.forName("org.mariadb.jdbc.Driver");
        conn = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public List<String> getColumn(String... columns) throws Exception {
        // 构建 SQL 查询语句
        StringBuilder query = new StringBuilder("SELECT ");
        for (int i = 0; i < columns.length; i++) {
            query.append(columns[i]);
            if (i < columns.length - 1) {
                query.append(", ");
            }
        }
        query.append(" FROM Emp;");

        PreparedStatement stmt = conn.prepareStatement(query.toString());
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            System.out.println(rs.getInt(1));
            System.out.println(rs.getString(2));
        }

        List<String> arr = new ArrayList<>();

        return arr;
    }

    public static void main(String[] args) {
        try {
            DbUtil db = new DbUtil();
            System.out.println("selecting......");
            System.out.println(db.getColumn("ENo", "Phone", "EName"));;
        }
        catch (Exception e) {
            System.out.println("Some wrong happen !");
            e.printStackTrace();
        }
    }
}
