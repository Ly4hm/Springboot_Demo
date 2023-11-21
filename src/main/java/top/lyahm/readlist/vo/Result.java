package top.lyahm.readlist.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Result {
    private int code;  // 状态码，0为失败， 1为成功
    private String message; // 要返回的消息
}
