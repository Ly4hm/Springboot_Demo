package top.lyahm.readlist.vo;

import lombok.Data;

@Data
public class HandleParam {
    public int Fid;
    private String newName;
    private int currState;

    // 阈值设置
    private int variety;
    private int changeIndex;
    private int newValue;

    private int newRoomID;
    private int test;

}
