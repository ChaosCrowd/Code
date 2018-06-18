package com.example.controller;

import com.example.service.IMenuService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api/*/dishes")
public class DishDetailController {

    @Autowired
    private IMenuService menuService;

    @ResponseBody
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public JSONObject getDishDetail(@RequestParam(value = "dishid", defaultValue = "-1") int id) {

        JSONObject  dish = new JSONObject();
        dish.put("msg", "OK");
        JSONObject jsonObject = JSONObject.fromObject(menuService.getGoodsById(id));
        dish.put("data", jsonObject);
        return dish;
    }
}