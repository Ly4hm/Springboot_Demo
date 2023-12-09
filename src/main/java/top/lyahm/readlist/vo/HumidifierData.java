package top.lyahm.readlist.vo;

import lombok.Data;

//加湿器数据类
@Data
public class HumidifierData {
    private int Fid;  //家具id
    private int Power; //功率
    private int Statue;//状态
    private int Threshold;
    private int Humi;
    private String RoomName;
}
