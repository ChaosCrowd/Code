package com.example.controller;

import com.example.pojo.Category;
import com.example.pojo.Goods;
import com.example.service.ICategoryService;
import com.example.service.IMenuService;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/api/*/dish")
public class DishController {
    @Autowired
    private IMenuService menuService;
    @Autowired
    private ICategoryService categoryService;

    /*
    增加菜式
     */
    @RequestMapping(path = "", method = RequestMethod.POST)
    @ResponseBody
    public String addDish(HttpServletRequest request, HttpServletResponse resp, HttpSession session) {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("Content-Type:application/json");
        resp.setStatus(200);

        /* 获取请求body信息 */
        String str;
        String reqBody = "";
        try {
            BufferedReader br = request.getReader();
            while((str = br.readLine()) != null){
                reqBody += str;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* 解析json字符串，并新增菜式 */
        String ret = "";
        try {
            JSONObject jo = new JSONObject(reqBody);
            String name = jo.getString("dishName");
            float price = (float)jo.getDouble("dishPrice");
            String img = jo.getString("dishImg");
            String des = jo.getString("dishDescription");
            JSONArray cateArr = jo.getJSONArray("categoryId");
            ArrayList<Category> cate = new ArrayList<>();
            for (int i = 0; i < cateArr.length(); i++) {
                int categoryId = cateArr.getInt(i);
                Category c = categoryService.getCategoryById(categoryId);
                cate.add(c);
            }

            Goods goods = new Goods(name, des, cate, price, img, 0);
            boolean flag =  menuService.addGoods(goods);

            if (!flag) {
                resp.setStatus(403);
                ret = "{\"msg\": \"Forbidden\",\"data\": null}";
            } else {
                ret += "{\"msg\": \" OK\",\"data\": {\"categoryID\": [";
                for (Category c : cate) {
                    ret += Integer.toString(c.getId()) + ",";
                }
                ret = ret.substring(0, ret.length()-1);
                ret += "],\"dishInfo\": {\"dishID\": \"" + Integer.toString(goods.getId());
                ret += "\",\"dishName\": \"" + name;
                ret += "\",\"dishPrice\": \"" + Float.toString(price);
                ret += "\",\"dishImg\": \"" + img;
                ret += "\",\"dishDescription\": \"" + des;
                ret += "\"}}}";
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }

    /*
    根据id删除菜式
    如果request的header已经规定accept类型，则response的body可以自动转换为接受的类型
     */
    @RequestMapping(path = "", method = RequestMethod.DELETE)
    @ResponseBody
    public String delDish(HttpServletRequest request, HttpServletResponse resp, HttpSession session) {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("Content-Type:application/json");
        resp.setStatus(200);

        /* 获取请求body信息 */
        String str;
        String reqBody = "";
        try {
            BufferedReader br = request.getReader();
            while((str = br.readLine()) != null){
                reqBody += str;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* 解析json字符串，并删除菜式 */
        String ret = "";
        try {
            JSONObject jo = new JSONObject(reqBody);
            int id = jo.getInt("dishID");

            Goods goods = menuService.getGoodsById(id);
            boolean flag = menuService.deleteGoodsById(id);
            if (!flag) {
                resp.setStatus(403);
                ret = "{\"msg\": \"Forbidden\",\"data\": null}";
            } else {
                ret += "{\"msg\": \" OK\",\"data\": {\"categoryID\": [";
                for (Category c : goods.getCate()) {
                    ret += Integer.toString(c.getId()) + ",";
                }
                ret = ret.substring(0, ret.length()-1);
                ret += "],\"dishInfo\": {\"dishID\": \"" + Integer.toString(id);
                ret += "\",\"dishName\": \"" + goods.getName();
                ret += "\",\"dishPrice\": \"" + Float.toString(goods.getPrice());
                ret += "\",\"dishImg\": \"" + goods.getImgSrc();
                ret += "\",\"dishDescription\": \"" + goods.getDesc();
                ret += "\"}}}";
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }

    /*
    修改菜式
     */
    @RequestMapping(path = "", method = RequestMethod.PATCH)
    @ResponseBody
    public String modifyDish(HttpServletRequest request, HttpServletResponse resp, HttpSession session) {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("Content-Type:application/json");
        resp.setStatus(200);

        /* 获取请求body信息 */
        String str;
        String reqBody = "";
        try {
            BufferedReader br = request.getReader();
            while((str = br.readLine()) != null){
                reqBody += str;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* 解析json字符串，并更改菜式 */
        String ret = "";
        try {
            JSONObject jo = new JSONObject(reqBody);
            int dishId = jo.getInt("dishID");
            String name = jo.getString("dishName");
            float price = (float)jo.getDouble("dishPrice");
            String img = menuService.getGoodsById(dishId).getImgSrc();
            String des = jo.getString("dishDescription");
            JSONArray cateArr = jo.getJSONArray("categoryId");
            ArrayList<Category> cate = new ArrayList<>();
            for (int i = 0; i < cateArr.length(); i++) {
                int categoryId = cateArr.getInt(i);
                Category c = categoryService.getCategoryById(categoryId);
                cate.add(c);
            }

            boolean flag;
            if (cateArr.length() == 0) {
                flag = menuService.deleteGoodsById(dishId);
            } else {
                Goods goods = menuService.getGoodsById(dishId);
                goods.setCate(cate);
                goods.setDesc(des);
                goods.setName(name);
                goods.setPrice(price);

                flag =  menuService.modifyGoods(goods);
            }

            if (!flag) {
                resp.setStatus(403);
                ret = "{\"msg\": \"Forbidden\",\"data\": null}";
            } else {
                ret += "{\"msg\": \" OK\",\"data\": {\"categoryID\": [";
                for (Category c : cate) {
                    ret += Integer.toString(c.getId()) + ",";
                }
                ret = ret.substring(0, ret.length()-1);
                ret += "],\"dishInfo\": {\"dishID\": \"" + Integer.toString(dishId);
                ret += "\",\"dishName\": \"" + name;
                ret += "\",\"dishPrice\": \"" + Float.toString(price);
                ret += "\",\"dishImg\": \"" + img;
                ret += "\",\"dishDescription\": \"" + des;
                ret += "\"}}}";
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }

    /*
    查找菜式
     */
    @RequestMapping(path = "", method = RequestMethod.GET)
    @ResponseBody
    public String getDishInfo(HttpServletRequest request, HttpServletResponse resp, HttpSession session) {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("Content-Type:application/json");
        resp.setStatus(200);

        /* 获取请求body信息 */
        String str;
        String reqBody = "";
        try {
            BufferedReader br = request.getReader();
            while((str = br.readLine()) != null){
                reqBody += str;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        /* 解析json字符串，并查找菜式 */
        /* !! 尚未处理目标菜式不存在的情况 */
        String ret = "";
        try {
            JSONObject jo = new JSONObject(reqBody);
            if (jo.getString("dishID") == "null" && jo.getString("categoryID") == "null") {
                /* 第三种情况，dishID和categoryID都为空时，返回所有菜式 */
                List<Goods> target = menuService.getGoodsList();
                ret += "{\"msg\": \"OK\",\"data\": [\"categoryID\": \"" + "null" + "\",";
                ret += "\"dishes\": [";
                for (Goods g : target) {
                    ret += "{\"id\": \"" + Integer.toString(g.getId()) + "\",\"name\": \"" + g.getName()
                            + "\",\"price\": \"" + Float.toString(g.getPrice()) + "\",\"img\": \"" + g.getImgSrc()
                            + "\",\"description\": \"" + g.getDesc() + "\"},";
                }
                ret = ret.substring(0, ret.length()-1);
                ret += "]]}";
            } else if (jo.getString("categoryID") != "null"){
                /* 第一种情况，categoryID不为空时，返回该类别下所有菜式 */
                int categoryID = jo.getInt("categoryID");
                Category c = categoryService.getCategoryById(categoryID);
                List<Goods> target = menuService.getGoodsListByCategory(c);
                ret += "{\"msg\": \"OK\",\"data\": [\"categoryID\": \"" + jo.getString("categoryID") + "\",";
                ret += "\"dishInfo\": [";
                for (Goods g : target) {
                    ret += "{\"dishId\": \"" + Integer.toString(g.getId()) + "\",\"dishName\": \"" + g.getName()
                            + "\",\"dishPrice\": \"" + Float.toString(g.getPrice()) + "\",\"dishImg\": \"" + g.getImgSrc()
                            + "\",\"dishDescription\": \"" + g.getDesc() + "\"},";
                }
                ret = ret.substring(0, ret.length()-1);
                ret += "]]}";
            } else {
                /* 第二种情况，categoryID为空且dishID不为空时，根据dishID返回菜式 */
                int dishId = jo.getInt("dishID");
                Goods target =  menuService.getGoodsById(dishId);
                int categoryID = 0;
                for (Category c : target.getCate()) {
                    categoryID = c.getId();
                }
                ret += "{\"msg\": \"OK\",\"data\": [\"categoryID\": \"" + Integer.toString(categoryID) + "\",";
                ret += "\"dishes\": [";
                ret += "{\"id\": \"" + Integer.toString(target.getId()) + "\",\"name\": \"" + target.getName()
                        + "\",\"price\": \"" + Float.toString(target.getPrice()) + "\",\"img\": \"" + target.getImgSrc()
                        + "\",\"description\": \"" + target.getDesc() + "\"}]]}";
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return ret;
    }
}
