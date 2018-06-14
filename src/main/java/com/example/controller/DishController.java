package com.example.controller;

import com.example.pojo.Category;
import com.example.pojo.Goods;
import com.example.service.ICategoryService;
import com.example.service.IMenuService;
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
            String name = jo.getString("name");
            float price = (float)jo.getDouble("price");
            String img = jo.getString("img");
            String des = jo.getString("description");
            int categoryId = jo.getInt("categoryId");

            Category c = categoryService.getCategoryById(categoryId);
            ArrayList<Category> cate = new ArrayList<>();
            cate.add(c);

            Goods goods = new Goods(name, des, cate, price, img, 0);
            boolean flag =  menuService.addGoods(goods);

            if (!flag) {
                resp.setStatus(403);
                ret = "{\"msg\": \"Forbidden\",\"data\": null}";
            } else {
                ret = "{\"msg\": \" OK\",\"data\": {\"id\": \"";
                ret += Integer.toString(goods.getId()) + "\"}}";
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
            int id = jo.getInt("id");
            String name = jo.getString("name");

            boolean flag = menuService.deleteGoodsById(id);
            if (!flag) {
                resp.setStatus(403);
                ret = "{\"msg\": \"Forbidden\",\"data\": null}";
            } else {
                ret = "{\"msg\": \" OK\",\"data\": {\"name\": \"" + name + "\"}}";
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
            String name = jo.getString("name");
            float price = (float)jo.getDouble("price");
            String img = jo.getString("img");
            String des = jo.getString("description");
            int categoryId = jo.getInt("categoryId");

            Category c = categoryService.getCategoryById(categoryId);
            ArrayList<Category> cate = new ArrayList<>();
            cate.add(c);

            /* modifyGoods的接口设计可能有些问题 */
            Goods goods = new Goods(name, des, cate, price, img, 0);
            boolean flag =  menuService.modifyGoods(goods);

            if (!flag) {
                resp.setStatus(403);
                ret = "{\"msg\": \"Forbidden\",\"data\": null}";
            } else {
                ret = "{\"msg\": \" OK\",\"data\": {\"dishID\": \"";
                ret += Integer.toString(dishId) + "\"}}";
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
        /* !! 未处理目标菜式不存在的情况 */
        String ret = "";
        try {
            JSONObject jo = new JSONObject(reqBody);
            if (jo.getString("dishID") == "null") {
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
            } else {
                int dishId = jo.getInt("dishID");
                int categoryId = jo.getInt("categoryId");
                Goods target =  menuService.getGoodsById(dishId);
                ret += "{\"msg\": \"OK\",\"data\": [\"categoryID\": \"" + Integer.toString(categoryId) + "\",";
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
