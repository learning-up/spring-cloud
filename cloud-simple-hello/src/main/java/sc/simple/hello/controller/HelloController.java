package sc.simple.hello.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RefreshScope
@RestController
@RequestMapping("hello")
public class HelloController {

    @Value("${mysqldb.datasource.url}")
    private String username;

    public void setUsername(String name){
        this.username = name;
    }

    @GetMapping("get")
    public String get(){
        return username;
    }

}
