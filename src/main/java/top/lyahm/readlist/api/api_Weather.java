package top.lyahm.readlist.api;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import top.lyahm.readlist.vo.IpLocation;

import java.util.Objects;

@SuppressWarnings("rawtypes")
@RestController
@RequestMapping("/api")
public class api_Weather {
    // 获取天气信息的api
    // 进行了一次封装，避免 key的泄露

    @SaCheckLogin
    @GetMapping("/weather_info")
    public ResponseEntity getInfo(HttpServletRequest request) throws Exception{
        String ip = request.getRemoteAddr();
        // 判断请求是否来自本地
        if (Objects.equals(ip, "127.0.0.1")) {
            ip = "124.160.64.90"; // 设为实验室ip
        }
        // 根据ip 获取定位
        String getIpUrl = String.format("https://restapi.amap.com/v3/ip?ip=%s&output=json&key=16bc988f2aebd1f8d7ad53136b473f9a", ip);
        RestTemplate restTemplate = new RestTemplate();
        // 发起GET请求并获取响应
        ResponseEntity<String> response = restTemplate.getForEntity(getIpUrl, String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        IpLocation LocationData = objectMapper.readValue(response.getBody(), IpLocation.class);
        String adcode = LocationData.getAdcode();

        // 通过反求出的 IP 数据获取天气
        String getWeatherUrl = String.format("https://restapi.amap.com/v3/weather/weatherInfo?key=16bc988f2aebd1f8d7ad53136b473f9a&city=%s", adcode);
        return restTemplate.getForEntity(getWeatherUrl, String.class);

    }
}
