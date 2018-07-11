package cloud.auth.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@Controller
public class TestController {

    @GetMapping("/test")
    public String test(Map<String,String> map){
        map.put("info","hello thymeleaf");
        return "welcome";
    }

}
