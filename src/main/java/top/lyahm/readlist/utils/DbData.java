package top.lyahm.readlist.utils;

import top.lyahm.readlist.vo.AtoiData;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.logging.Level;
import java.util.logging.Logger;


public class DbData {
    private static final Logger LOGGER = Logger.getLogger(DbUser.class.getName());
    public static ArrayList<AtoiData> getSensorData() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int[] choice = {1,4,5};
        ArrayList<AtoiData> SensorList = new ArrayList<>();
        String tmpTime=null;

        try {
            conn = DbUtil.getConnection();
            String sql = "SELECT Reading,Date FROM SensorDataAnalysis WHERE Sid=?";
            for(int i=0;i<3;i++){
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,choice[i]);

                rs = pstmt.executeQuery();

                while(rs.next()) {
                    AtoiData AData=new AtoiData();
                    AData.setAData(rs.getFloat(1));

                    tmpTime= rs.getString(2);
                    DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                    AData.setDate(LocalDateTime.parse(tmpTime,df));
//                如果access码为0，则设置isAdmin为true
//                如果 email 的值不为null，则设置haveEmail为true
                    SensorList.add(AData);
                }
            }
            SensorList.sort(Comparator.comparing(AtoiData::getDate).reversed());
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,"数据库异常： "+ e.getMessage(),e);
        } finally {
            DbUtil.release(conn, pstmt, rs);
        }

        return SensorList; // 如果发生异常，默认返回 false
    }

    public static void main(String[] args){
        ArrayList<AtoiData> sensorData = DbData.getSensorData();

        for (AtoiData data : sensorData) {
            System.out.println("Reading: " + data.getAData() + " Date: " + data.getDate());
        }
    }
}
