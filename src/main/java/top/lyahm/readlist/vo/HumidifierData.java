package top.lyahm.readlist.vo;

import lombok.Data;

//加湿器数据类
@Data
public class HumidifierData extends FurnitureData{
    private int Threshold;
    private int Humi;
}
