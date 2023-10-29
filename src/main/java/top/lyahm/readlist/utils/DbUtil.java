package top.lyahm.readlist.utils;

import org.springframework.web.servlet.view.RedirectView;

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

    public static boolean waf(String query) {
        String badStr = "'|and|exec|execute|insert|select|delete|update|count|drop|*|%|chr|mid|master|truncate|" +
                "char|declare|sitename|net user|xp_cmdshell|;|or|-|+|,|like'|and|exec|execute|insert|create|drop|" +
                "table|from|grant|use|group_concat|column_name|" +
                "information_schema.columns|table_schema|union|where|select|delete|update|order|by|count|*|" +
                "chr|mid|master|truncate|char|declare|or|;|-|--|+|,|like|//|/|%|#";//过滤掉的sql关键字，可以手动添加

        // 长度校验
        if (query.length() > 10) {
            return true;
        }

        // 黑名单校验
        query = query.toLowerCase();//统一转为小写
        String[] badStrs = badStr.split("\\|");
        for (String str : badStrs) {
            if (query.contains(str)) {
                return true;
            }
        }
        return false;
    }

    public ResultSet getColumn(String table, String... columns) throws Exception {
        // 构建 SQL 查询语句
        StringBuilder query = new StringBuilder("SELECT ");
        for (int i = 0; i < columns.length; i++) {
            query.append(columns[i]);
            if (i < columns.length - 1) {
                query.append(", ");
            }
        }
        query.append(" FROM &;".replace("&", table));

        PreparedStatement stmt = conn.prepareStatement(query.toString());
        return stmt.executeQuery();
    }

    public void insertToTmp(String name, Integer age) throws Exception {
        // INSERT INTO 表名 (列1, 列2, 列3, ...) VALUES (值1, 值2, 值3, ...);
        String query = String.format("insert into tmp (name, age) values ('%s', %d);", name, age);
        System.out.println(query);
        if (waf(name) || waf(age.toString())) { new RedirectView("/selectAll"); }
        else {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.executeQuery();
        }
    }

    public void deleteFromTmp(String name) throws Exception {
        // DELETE FROM users WHERE name = 30;
        String query = String.format("DELETE FROM tmp WHERE name = '%s'", name);
        System.out.println(query);
        if (waf(query)) { new RedirectView("/selectAll"); }
        else {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.executeQuery();
        }
    }

    public void updateFromTmp(String name, Integer age) throws Exception {
        // UPDATE users SET status = 'inactive' WHERE age > 30;
        String query = String.format("UPDATE tmp SET age = %d WHERE name = '%s'", age, name);
        System.out.println(query);
        if (waf(name) || waf(age.toString())) { new RedirectView("/selectAll"); }
        else {
            PreparedStatement stmt = conn.prepareStatement(query);
            stmt.executeQuery();
        }
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
//            System.out.println("selecting......");
//            System.out.println(DbUtil.echoResult(db.getColumn("Emp", "ENo", "Phone", "EName")));
            db.updateFromTmp("lyh", 12);
        }
        catch (Exception e) {
            System.out.println("Some wrong happen !");
        }
    }
}
