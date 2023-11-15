package top.lyahm.readlist.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {
    private int code;  // 状态码
    private String message;
}
