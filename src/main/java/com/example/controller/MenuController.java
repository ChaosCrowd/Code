package com.example.controller;

import com.example.pojo.Category;
import com.example.pojo.Goods;
import com.example.service.ICategoryService;
import com.example.service.IMenuService;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/api/*/menu")
public class MenuController {
    @Autowired
    private IMenuService menuService;
    private ICategoryService categoryService;

    @ResponseBody
    @RequestMapping(path = "", method = RequestMethod.GET)
    public JSONObject getMenu() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("msg", "OK");
        List<JSONObject> data = new ArrayList<JSONObject>();

        List<Category> cateList = categoryService.getCategoriesList();

        for (Category x : cateList) {

            JSONObject temp = JSONObject.fromObject(x);
            JSONObject cateoryJSON = new JSONObject();
            cateoryJSON.put("category", temp);


            List<JSONObject> goodsjson = new ArrayList<JSONObject>();
            List<Goods> menulist = menuService.getGoodsListByCategory(x);
            for (Goods y : menulist) {
                JSONObject temp3 = JSONObject.fromObject(y);
                goodsjson.add(temp3);
            }
            cateoryJSON.put("goods",goodsjson);
            data.add(cateoryJSON);
        }
        jsonObject.put("data", data);
        return jsonObject;
    }
}
