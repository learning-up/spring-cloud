package cloud.auth.server.controller;

import cloud.auth.server.model.UserInfo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(Model model, UserInfo user){
        model.addAttribute("user",user);
        return "login";
    }

//    @PostMapping("logout")
//    public String logout(Model model){
//        User user = new User();
//        model.addAttribute("user",user);
//        return "login";
//    }

}
