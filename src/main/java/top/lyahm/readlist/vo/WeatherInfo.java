package top.lyahm.readlist.vo;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
class Lives {
    private String province;
    private String city;
    private String adcode;
    private String weather;
    private String temperature;
    private String winddirection;
    private String windpower;
    private String humidity;
    private Date reporttime;
    private String temperature_float;
    private String humidity_float;
}
@Data
public class WeatherInfo {
    private String status;
    private String count;
    private String info;
    private String infocode;
    private List<Lives> lives;
}


