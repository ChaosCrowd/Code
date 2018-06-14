package com.example.controller;

import com.example.pojo.Category;
import com.example.pojo.Goods;
import com.example.service.ICategoryService;
import com.example.service.IMenuService;

//import com.sun.deploy.net.HttpResponse;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/api/*/menu")
public class MenuController extends HttpServlet {
    @Autowired
    private IMenuService menuService;

    @Autowired
    private ICategoryService categoryService;

    @ResponseBody
    @RequestMapping(path = "", method = RequestMethod.GET)
    public String getMenu(HttpServletRequest request, HttpServletResponse response){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("Content-Type:application/json");

        //获取所有菜品
        ArrayList<Category> cateList = categoryService.getCategoriesList();

        JSONObject jsonObject = new JSONObject();

        //json加入msg
        jsonObject.put("msg", "OK");



        ArrayList<JSONObject> data = new ArrayList<JSONObject>();
        //menuService.deleteGoodsById(1)
       for (Category x : cateList) {

            JSONObject temp = JSONObject.fromObject(x);
            JSONObject cateoryJSON = new JSONObject();
            cateoryJSON.put("category", temp);


            ArrayList<JSONObject> goodsjson = new ArrayList<JSONObject>();
            //获取每个菜品对应的所有菜
            List<Goods> menulist = menuService.getGoodsListByCategory(x);
            for (Goods y : menulist) {
                JSONObject temp3 = JSONObject.fromObject(y);
                goodsjson.add(temp3);
            }
            cateoryJSON.put("goods",goodsjson);
            data.add(cateoryJSON);
        }
        jsonObject.put("data", data);
        return jsonObject.toString();
    }
}
