package com.example.controller;

import com.example.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/api/order")
public class OrderController {
    @Autowired
    private IOrderService orderService;

}
