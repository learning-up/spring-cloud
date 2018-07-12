package cloud.auth.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
public class TestController {

    @GetMapping("/test")
    public String test(Map<String,String> map){
        map.put("info","hello thymeleaf");
        return "welcome";
    }

    @GetMapping("/test1")
    @ResponseBody
    public String test1() {
        return "test1";
    }

    @GetMapping("/api/test2")
    @ResponseBody
    public String test2() {
        return "test2";
    }

    @GetMapping("/manage/test3")
    @ResponseBody
    public String test3() {
        return "test3";
    }

}
