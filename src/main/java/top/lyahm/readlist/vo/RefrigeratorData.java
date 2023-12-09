package top.lyahm.readlist.vo;

import lombok.Data;

//冰箱数据类
@Data
public class RefrigeratorData {
    private int Fid;  //家具id
    private int Power; //功率
    private int Statue;//状态
    private int RefrigerationThreshold;
    private int FrozenThreshold;
    private String RoomName;
}
