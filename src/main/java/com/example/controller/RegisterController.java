package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/home/login")
public class RegisterController {
    @RequestMapping(value="/test", method = RequestMethod.POST)
    public String test(@RequestParam String username, @RequestParam String password, Model model){
        if (username.equals("admin") && password.equals("1234")) {
            model.addAttribute("username", username);
            return "html/succeed";
        } else {
            model.addAttribute("username", username);
            return "html/fail";
        }

    }
}
