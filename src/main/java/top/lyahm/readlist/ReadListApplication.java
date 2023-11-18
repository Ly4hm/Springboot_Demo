package top.lyahm.readlist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages={"com.pj", "top.lyahm.readlist"})
public class ReadListApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReadListApplication.class, args);
    }

}
