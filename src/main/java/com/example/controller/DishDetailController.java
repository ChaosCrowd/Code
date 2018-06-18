package com.example.controller;

import com.example.service.IMenuService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

@Controller
@RequestMapping("/api/*/dishes")
public class DishDetailController extends HttpServlet {

    @Autowired
    private IMenuService menuService;

    @ResponseBody
    @RequestMapping(value = "/detail", method = RequestMethod.GET)
    public String getDishDetail(@RequestParam(value = "dishid", defaultValue = "-1") int id, HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("Content-Type:application/json");

        JSONObject  dish = new JSONObject();
        dish.put("msg", "OK");
        JSONObject jsonObject = JSONObject.fromObject(menuService.getGoodsById(id));
        dish.put("data", jsonObject);
        return dish.toString();
    }
}