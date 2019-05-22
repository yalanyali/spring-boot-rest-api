package rest.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path="/api/auth")
public class AuthController {

    @GetMapping(path="")
    public @ResponseBody
    String checkAuth() {
        return "{ \"success\": true }";
    }

}