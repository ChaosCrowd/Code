package com.example.controller;

import com.example.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/api/menu")
public class MenuController {
    @Autowired
    private IMenuService menuService;

}
