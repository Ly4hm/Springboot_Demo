package top.lyahm.readlist.vo;

import lombok.Data;

@Data
public class User {
    private int id;
    private String name;
    private String passwd;  // hash
    private int access;
    private String email;
}
