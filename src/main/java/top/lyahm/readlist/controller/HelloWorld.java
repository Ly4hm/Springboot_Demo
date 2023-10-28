package top.lyahm.readlist.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorld {

    @GetMapping("/search")
    public String hello() {
        return "Hello world!!! this is a spring_boot demo.";
    }
}
