package top.lyahm.readlist.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class AtoiData {
//    数据记录时间
    private LocalDateTime Date;
//    AData为三类传感器的数据
//    private float AData;
    private float Temp;
    private float Humi;
    private float Light;
}
