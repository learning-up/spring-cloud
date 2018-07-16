package cloud.auth.server.controller;

import cloud.auth.server.model.UserInfo;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {

//    @Value("")
    @Setter private String zuulPath = "http://192.168.0.212:8884";

    @GetMapping("/login")
    public String login(HttpServletRequest request, Model model){
        model.addAttribute("zuulPath", request.getParameter(zuulPath));
        model.addAttribute("user", new UserInfo());
        return "login";
    }

//    @PostMapping("logout")
//    public String logout(Model model){
//        User user = new User();
//        model.addAttribute("user",user);
//        return "login";
//    }

}
