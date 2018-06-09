package com.example.controller;

import com.example.pojo.Goods;
import com.example.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/api/*/menu")
public class MenuController {
    @Autowired
    private IMenuService menuService;

    @ResponseBody
    @RequestMapping(path = "", method = RequestMethod.GET)
    public String getMenu() {
        List<Goods> list = menuService.getGoodsList();
        String ret = "";
        for (Goods goods : list) {
            ret += goods.toString() + "\n";
        }
        return ret;
    }
}
