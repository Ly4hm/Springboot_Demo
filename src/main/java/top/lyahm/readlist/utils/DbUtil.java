package top.lyahm.readlist.utils;

import java.sql.*;

public class DbUtil {
    private static final String URL = "jdbc:mariadb://47.97.16.22:3306/CInfo";
    private static final String USER = "root";
    private static final String  PASSWORD = "python666";
    private static Connection conn = null;

    public DbUtil() throws Exception{
        Class.forName("org.mariadb.jdbc.Driver");
        conn = DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public ResultSet getColumn(String... columns) throws Exception {
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
        return stmt.executeQuery();
    }

    public static String echoResult(ResultSet rs) throws Exception{
        // 获取结果原数据用于分析类型
        ResultSetMetaData metaData = rs.getMetaData();
        int columnCount = metaData.getColumnCount();
        StringBuilder text = new StringBuilder("select result:\n");

        // 输出列名
        for (int i = 1; i <= columnCount; i++) {
            String columnName = metaData.getColumnName(i);
            text.append(columnName);
            // 输出属性间空格
            text.append(" ");
        }

        while (rs.next()) {
            // 换行开始输出数据
            text.append("\n");

            // 遍历列处理
            for (int i = 1; i <= columnCount; i++) {
                String columnTypeName = metaData.getColumnTypeName(i);

                // 根据不同类型实现分支
                switch (columnTypeName) {
                    case "INTEGER":
                        text.append(rs.getInt(i));
                        break;
                    case "VARCHAR":
                        text.append(rs.getString(i));
                        break;
                }
                // 输出属性间空格
                text.append(" ");
            }
        }
        return text.toString();
    }

    public static void main(String[] args) {
        try {
            DbUtil db = new DbUtil();
            System.out.println("selecting......");
            System.out.println(DbUtil.echoResult(db.getColumn("ENo", "Phone", "EName")));
        }
        catch (Exception e) {
            System.out.println("Some wrong happen !");
            e.printStackTrace();
        }
    }
}
