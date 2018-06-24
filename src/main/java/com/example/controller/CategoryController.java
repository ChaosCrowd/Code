package com.example.controller;


import com.example.pojo.Category;
import com.example.service.ICategoryService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller


public class CategoryController extends HttpServlet {
    @Autowired
    private ICategoryService categoryService;

    @ResponseBody
    @RequestMapping(value = "/api/*/query/category", method = RequestMethod.GET)
    public String getCategory(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("Content-Type:application/json");


        JSONObject json = new JSONObject();

        List<Category> temp = categoryService.getCategoriesList();
        if (temp.size() != 0) {
            response.setStatus(200);
            json.put("msg", "OK");
            JSONArray temp1 =  JSONArray.fromObject(temp);
            json.put("data", temp1);
            return json.toString();
        } else {
            response.setStatus(403);
            json.put("msg", "Forbidden");
            json.put("data", "");
            return  json.toString();
        }

    }


    //修改菜品
    @ResponseBody
    @RequestMapping(value = "/api/*/modify/category", method = RequestMethod.POST)
    public String renameCate(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("Content-Type:application/json");
        JSONObject result = new JSONObject();
        JSONObject temp = JSONObject.fromObject(data);
        String temp_id = temp.getString("categoryID");
        String temp_name = temp.getString("categoryName");
        int id = Integer.parseInt(temp_id);
        Category temp1 = categoryService.getCategoryById(id);
        temp1.setName(temp_name);
        if (categoryService.modifyCategory(temp1)) {
            response.setStatus(200);
            //修改
            result.put("msg", "OK");
            JSONObject temp3 = new JSONObject();
            //修改
            temp3.put("categoryID", temp_id);
            temp3.put("categoryName", temp_name);
            result.put("data", temp3);
            return result.toString();
        } else {
            response.setStatus(403);
            result.put("msg", "Forbidden");
            result.put("data", "");
            return result.toString();
        }
    }

    //增加菜品类型

    @ResponseBody
    @RequestMapping(value = "/api/*/add/category", method = RequestMethod.POST)
    public String  addCate(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("Content-Type:application/json");
        JSONObject res = new JSONObject();


        JSONObject paramJSON = JSONObject.fromObject(data);
        String tempname = paramJSON.getString("categoryName");
        Category temp = new Category();
        temp.setName(tempname);
        if (categoryService.addCategory(temp)) {
            int newid = temp.getId();
            response.setStatus(200);
            res.put("msg", "OK");
            JSONObject temp1 = new JSONObject();
            temp1.put("categoryID", newid);
            temp1.put("categoryName", tempname);
            res.put("data", temp1);
            return res.toString();
        } else {
            response.setStatus(403);
            res.put("msg", "Forbidden");
            res.put("data", "");
            return res.toString();
        }
    }

    //删除菜品
    @ResponseBody
    @RequestMapping(value = "/api/*/delete/category", method = RequestMethod.GET)
    public String deleteCate(@RequestParam(value = "categoryID", defaultValue = "-1") int id, HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("Content-Type:application/json");

        JSONObject res = new JSONObject();

        Category cate = categoryService.getCategoryById(id);
        String tempname = cate.getName();
        if (categoryService.deleteCategoryById(id)) {
            response.setStatus(200);
            res.put("msg", "OK");
            JSONObject temp1 = new JSONObject();
            temp1.put("categoryID", id);
            temp1.put("categoryName", tempname);
            res.put("data", temp1);
            return res.toString();
        } else {
            response.setStatus(403);
            res.put("msg", "Forbidden");
            res.put("data", "");
            return res.toString();
        }
    }

}
