package top.lyahm.readlist.vo;

import lombok.Data;

//空调数据类
@Data
public class airConditionerData {
    private int Fid;  //家具id
    private int Power; //功率
    private int Statue;//状态,0关闭，1开启，2异常
    private int MaxTemp; //上限阈值
    private int MinTemp;//下限阈值
    private int WSpeed;
    private String RoomName;
}
