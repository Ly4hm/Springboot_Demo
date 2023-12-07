package top.lyahm.readlist.vo;

import lombok.Data;

//冰箱数据类
@Data
public class RefrigeratorData extends FurnitureData{
    private int RefrigerationThreshold;
    private int FrozenThreshold;
}
