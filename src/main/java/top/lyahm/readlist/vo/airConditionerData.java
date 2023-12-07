package top.lyahm.readlist.vo;

import lombok.Data;

//空调数据类
@Data
public class airConditionerData extends FurnitureData{
    private int MaxTemp; //上限阈值
    private int MinTemp;//下限阈值
    private int WSpeed;
}
