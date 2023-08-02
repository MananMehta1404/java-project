package com.example.javaproject.controller;

import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private SunbaseAPIClient sunbaseAPIClient = new SunbaseAPIClient();
    
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String welcome(ModelMap model, @RequestParam String login_id, @RequestParam String password) {
        try{
            sunbaseAPIClient.authenticate(login_id, password);
            model.put("user", login_id);
            return "welcome";
        } catch (IOException e) {
            model.put("errorMessage", "Invalid login_id or password");
            return "login";
        }
    }
}
