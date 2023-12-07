package top.lyahm.readlist.utils;

import top.lyahm.readlist.vo.AtoiData;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class DbData {
    private static final Logger LOGGER = Logger.getLogger(DbUser.class.getName());

//    返回传感器的最新五条数据
    public static ArrayList<AtoiData> getSensorData() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int[] choice = {1,4,5};
        ArrayList<AtoiData> SensorList = new ArrayList<>();
        String tmpTime=null;

        float[] TempData=new float[20];
        float[] LightData=new float[20];
        float[] HumiData=new float[20];

//        HashMap<Integer,Float> preData=new HashMap<>();
        HashMap<Integer,LocalDateTime> preDate=new HashMap<>();

        try {
            conn = DbUtil.getConnection();
            String sql = "SELECT Reading,Date FROM SensorDataAnalysis WHERE Sid=?";
            for(int i=0;i<3;i++) {
                int indexTime = 0;
                int indexH = 0;
                int indexL = 0;
                int indexT = 0;
//                i=0读取温度传感器，i=1读取光照传感器，i=2读取湿度传感器
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, choice[i]);


                rs = pstmt.executeQuery();


                while (rs.next()) {
//                    AData.setAData(rs.getFloat(1));
//                    日期只读一遍
                    if(i==0){
                        tmpTime = rs.getString(2);
                        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        preDate.put(indexTime, LocalDateTime.parse(tmpTime, df));
                        indexTime++;
                    }
//                    AData.setDate(LocalDateTime.parse(tmpTime,df));
//                如果access码为0，则设置isAdmin为true
//                如果 email 的值不为null，则设置haveEmail为true
//                    SensorList.add(AData);
                    if (i == 0) {
//                        AData.setTemp(rs.getFloat(1));
//                        preData.put(i+1, rs.getFloat(1));
                        TempData[indexT] = rs.getFloat(1);
                        indexT++;
                    } else if (i == 1) {
//                        preData.put(i+1, rs.getFloat(1));

                        LightData[indexL] = rs.getFloat(1);
                        indexL++;
                    } else {
//                        preData.put(i+1, rs.getFloat(1));
//                        LightData[indexL] = rs.getFloat(1);
//                        indexL++;
                        HumiData[indexH] = rs.getFloat(1);
                        indexH++;
                    }
                }

            }
            for(int j=0;j<5;j++){
                AtoiData AData=new AtoiData();
                AData.setDate(preDate.get(j));
                AData.setTemp(TempData[j]);
                AData.setHumi(HumiData[j]);
                AData.setLight(LightData[j]);
                SensorList.add(AData);
            }

            SensorList.sort(Comparator.comparing(AtoiData::getDate).reversed());
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,"数据库异常： "+ e.getMessage(),e);
        } finally {
            DbUtil.release(conn, pstmt, rs);
        }

        return SensorList; // 如果发生异常，默认返回 false
    }

    public static void storageData(){
        ArrayList<AtoiData> sensorData=getSensorData();
        float Temp=sensorData.get(0).getTemp();
        float Humi=sensorData.get(0).getHumi();
        float Light=sensorData.get(0).getLight();

        String storagedData=new GPTUtil().getAnalyse(Temp,Humi,Light);
        DbFurniture.updateGMSG(storagedData);
    }
    public static void main(String[] args){
//        System.out.println(storageData());
        System.out.println(getSensorData());
    }
}
