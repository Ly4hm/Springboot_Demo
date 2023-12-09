package top.lyahm.readlist.vo;

import lombok.Data;

//窗帘数据类
@Data
public class CurtainData {
    private int Fid;  //家具id
    private int Power; //功率
    private int Statue;//状态
    private int Threshold; //状态阈值
    private String RoomName;
}
