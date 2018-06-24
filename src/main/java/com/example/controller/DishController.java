package com.example.controller;

import com.example.pojo.Category;
import com.example.pojo.Goods;
import com.example.service.ICategoryService;
import com.example.service.IMenuService;
import net.sf.json.JSON;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Controller
public class DishController {
    @Autowired
    private IMenuService menuService;
    @Autowired
    private ICategoryService categoryService;




    /*商家端api*/

    /*
    增加菜式
     */
    @RequestMapping(value = "/api/*/add/dish", method = RequestMethod.POST)
    @ResponseBody
    public String addDish(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("Content-Type:application/json");
        JSONObject res = new JSONObject();

        /* 获取请求body信息 */
        JSONObject json = JSONObject.fromObject(data);
        String dishname = json.getString("dishName");
        String dishprice = json.getString("dishPrice");
        String dishimage = json.getString("dishImg");
        //String volume = json.getString("dishVolume");
        String dishDesc = json.getString("dishDescription");
        String cateid = json.getString("categoryID");
        String demosub = cateid.substring(1,cateid.length()-1);
        String demoArray[] = demosub.split(",");
        List<String> tempList = Arrays.asList(demoArray);
        List<Integer> tempint = new ArrayList<Integer>();
        ArrayList<Category> catelist = new ArrayList<Category>();
        for (int i = 0; i < tempList.size(); i++) {
            System.out.println(Integer.parseInt(tempList.get(i)));
            tempint.add(Integer.parseInt(tempList.get(i)));
            catelist.add(categoryService.getCategoryById(Integer.parseInt(tempList.get(i))));
        }
        Goods good = new Goods(dishname, dishDesc, catelist, Float.parseFloat(dishprice), dishimage, 0);
        int dishid = good.getId();
        if (menuService.addGoods(good)) {
            response.setStatus(200);
            res.put("msg", "OK");
            JSONObject tempjson = new JSONObject();
            JSONObject tempjson1 = new JSONObject();
            tempjson.put("categoryID", tempint);
            tempjson1.put("dishID", dishid);
            tempjson1.put("dishName", dishname);
            tempjson1.put("dishPrice", Float.parseFloat(dishprice));
            tempjson1.put("dishImg", dishimage);
            tempjson1.put("dishDescription", dishDesc);
            tempjson.put("dishInfo", tempjson1);
            res.put("data", tempjson);
            return res.toString();
        } else {
            response.setStatus(200);
            res.put("msg", "没有权限");
            res.put("data","");
            return  res.toString();
        }
    }

    /*删除菜式*/
    @RequestMapping(value = "/api/*/delete/dish", method = RequestMethod.GET)
    @ResponseBody
    public String delDish(@RequestParam(value = "dishID", defaultValue = "-1") int dishid,HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("Content-Type:application/json");

        JSONObject res = new JSONObject();
        System.out.println(dishid);
        Goods good = menuService.getGoodsById(dishid);
        if (good != null) {
            menuService.deleteGoodsById(dishid);
            response.setStatus(200);
            res.put("msg", "OK");
            String dishname = good.getName();
            float dishprice = good.getPrice();
            String dishimage = good.getImgSrc();
            //String volume = json.getString("dishVolume");
            String dishDesc = good.getDesc();
            ArrayList<Category> cateList = good.getCate();
            List<Integer> cateid =  new ArrayList<Integer>();
            for (int i = 0; i < cateList.size(); i++) {
                cateid.add(cateList.get(i).getId());
            }
            JSONObject tempjson = new JSONObject();
            JSONObject tempjson1 = new JSONObject();
            tempjson.put("categoryID", cateid);
            tempjson1.put("dishID", good.getId());
            tempjson1.put("dishName", dishname);
            tempjson1.put("dishPrice", dishprice);
            tempjson1.put("dishImg", dishimage);
            tempjson1.put("dishDescription", dishDesc);
            tempjson.put("dishInfo", tempjson1);
            res.put("data", tempjson);
            return res.toString();
        } else {
            response.setStatus(403);
            res.put("msg", "没有权限");
            res.put("data", "");
            return res.toString();
        }

    }

    /*修改菜式*/
    @RequestMapping(value = "/api/*/modify/dish", method = RequestMethod.POST)
    @ResponseBody
    public String modifyDish(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("Content-Type:application/json");
        JSONObject res = new JSONObject();

        /* 获取请求body信息 */
        JSONObject json = JSONObject.fromObject(data);
        String dishname = json.getString("dishName");
        String dishprice = json.getString("dishPrice");
        String dishimage = json.getString("dishImg");
        //String volume = json.getString("dishVolume");
        String dishDesc = json.getString("dishDescription");
        String cateid = json.getString("categoryID");
        String demosub = cateid.substring(1,cateid.length()-1);
        String demoArray[] = demosub.split(",");
        List<String> tempList = Arrays.asList(demoArray);
        List<Integer> tempint = new ArrayList<Integer>();
        ArrayList<Category> catelist = new ArrayList<Category>();
        for (int i = 0; i < tempList.size(); i++) {
            System.out.println(Integer.parseInt(tempList.get(i)));
            tempint.add(Integer.parseInt(tempList.get(i)));
            catelist.add(categoryService.getCategoryById(Integer.parseInt(tempList.get(i))));
        }
        Goods good = new Goods(dishname, dishDesc, catelist, Float.parseFloat(dishprice), dishimage, 0);
        int dishid = good.getId();
        if (menuService.modifyGoods(good)) {
            response.setStatus(200);
            res.put("msg", "OK");
            JSONObject tempjson = new JSONObject();
            JSONObject tempjson1 = new JSONObject();
            tempjson.put("categoryID", tempint);
            tempjson1.put("dishID", dishid);
            tempjson1.put("dishName", dishname);
            tempjson1.put("dishPrice", Float.parseFloat(dishprice));
            tempjson1.put("dishImg", dishimage);
            tempjson1.put("dishDescription", dishDesc);
            tempjson.put("dishInfo", tempjson1);
            res.put("data", tempjson);
            return res.toString();
        } else {
            response.setStatus(200);
            res.put("msg", "没有权限");
            res.put("data","");
            return  res.toString();
        }
    }

    /*查找菜式*/
    @RequestMapping(value = "/api/*/query/dish", method = RequestMethod.GET)
    @ResponseBody
    public String getDish(@RequestParam(value = "categoryID", defaultValue = "-1") int id,HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("Content-Type:application/json");

        JSONObject res = new JSONObject();

        Category cate = categoryService.getCategoryById(id);
        List<Integer> categoryid = new ArrayList<Integer>();
        categoryid.add(id);
        if (cate != null) {
            response.setStatus(200);
            res.put("msg", "OK");
            List<Goods> dishlist = menuService.getGoodsListByCategory(cate);
            JSONArray templist = new JSONArray();
            JSONObject tempjson = new JSONObject();
            tempjson.put("categoryID", categoryid);
            for (int i = 0; i < dishlist.size(); i++) {
                Goods good = dishlist.get(i);
                String dishname = good.getName();
                float dishprice = good.getPrice();
                String dishimage = good.getImgSrc();
                String dishDesc = good.getDesc();
                //String volume = json.getString("dishVolume"
                JSONObject tempjson1 = new JSONObject();
                tempjson1.put("dishID", good.getId());
                tempjson1.put("dishName", dishname);
                tempjson1.put("dishPrice", dishprice);
                tempjson1.put("dishImg", dishimage);
                tempjson1.put("dishDescription", dishDesc);
                templist.add(tempjson1);
            }
            tempjson.put("dishInfo", templist);
            res.put("data", tempjson);
            return res.toString();
        } else {
            response.setStatus(403);
            res.put("mag", "没有权限");
            res.put("data", "");
            return res.toString();
        }

    }

}