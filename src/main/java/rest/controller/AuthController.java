package rest.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import rest.response.SuccessResponse;

import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8_VALUE;

@Controller
@RequestMapping(path="/api/auth", produces = APPLICATION_JSON_UTF8_VALUE)
public class AuthController {

    @GetMapping(path="")
    public @ResponseBody
    SuccessResponse checkAuth() {
        return new SuccessResponse();
    }

}