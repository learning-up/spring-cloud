package sc.simple.hello.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import sc.simple.hello.feign.HelloFeign;

@RestController
@RequestMapping("hello")
public class HelloController {

    @Autowired
    HelloFeign helloFeign;

    @GetMapping("get")
    public String get(){
        return helloFeign.get();
    }

}
