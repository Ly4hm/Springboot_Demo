package top.lyahm.readlist.vo;

import lombok.Data;

@Data
public class IpLocation {
    private int status;
    private String info;
    private int infocode;
    private String province;
    private String city;
    private String adcode;
    private String rectangle;
}