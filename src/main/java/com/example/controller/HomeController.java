package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/home")
public class HomeController {

    @RequestMapping(value = "")
    @ResponseBody
    public String home() {
        //System.out.println("home page");
        return "asdasdasdasdasdasdasdasdasdasdasasd";
    }

    @RequestMapping("/login")
    public String toLog() {
        return "html/login";
    }

}
