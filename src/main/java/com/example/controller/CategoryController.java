package com.example.controller;


import com.example.pojo.Category;
import com.example.service.ICategoryService;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping(value = "/api/*/category")

public class CategoryController extends HttpServlet {
    @Autowired
    private ICategoryService categoryService;

    @ResponseBody
    @RequestMapping(path = "", method = RequestMethod.GET)
    public String getCategory(HttpServletRequest request, HttpServletResponse response) {
        JSONObject json = new JSONObject();
        json.put("msg", "OK");
        List<Category> temp = categoryService.getCategoriesList();
        JSONArray temp1 =  JSONArray.fromObject(temp);
        json.put("data", temp1);
        return json.toString();
    }


    //修改菜品名
    @ResponseBody
    @RequestMapping(method = RequestMethod.PATCH)
    public void renameCate(@RequestBody JSONObject data, HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("Content-Type:application/json");

        String tempid = data.getString("id");
        String tempname = data.getString("name");
        int id = Integer.parseInt(tempid);
        Category temp = categoryService.getCategoryById(id);
        temp.setName(tempname);
        categoryService.modifyCategory(temp);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST)
    public void addCate(@RequestBody JSONObject data, HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("Content-Type:application/json");

        String tempname = data.getString("name");
        Category temp = new Category();
        temp.setName(tempname);
        categoryService.addCategory(temp);
    }

    @ResponseBody
    @RequestMapping(method = RequestMethod.DELETE)
    public void deleteCate(@RequestBody JSONObject data,  HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("Content-Type:application/json");
        String tempid = data.getString("id");
        int id = Integer.parseInt(tempid);
        categoryService.deleteCategoryById(id);
    }

}
