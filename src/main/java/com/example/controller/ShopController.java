package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 店铺信息管理controller
 */
@Controller
@RequestMapping(path = "/api/*/shop", produces = "application/json;charset=utf-8;")
public class ShopController {
}
