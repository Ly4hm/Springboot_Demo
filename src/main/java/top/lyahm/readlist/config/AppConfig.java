package top.lyahm.readlist.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import top.lyahm.readlist.bean.Emp;

@Configuration  // 标记这是一个配置类, 给容器中注册组件
public class AppConfig {
//    @Scope("prototype")   // 标记为多实例对象
    @Bean("Emp")   // 将自定义的类放入 Spring 的容器中
    public Emp emp() {
        var emp = new Emp();
        emp.setId(1);
        emp.setName("lyahm");

        return emp;
    }
}
