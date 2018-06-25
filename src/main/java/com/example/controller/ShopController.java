package com.example.controller;

import com.example.pojo.ShopInfo;
import com.example.service.IShopService;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 店铺信息管理controller
 */
@Controller
@RequestMapping(produces = "application/json;charset=utf-8;")
public class ShopController {
    @Autowired
    private IShopService shopinfoService;
    @ResponseBody
    @CrossOrigin
    @RequestMapping(value = "/api/*/modify/basic", method = RequestMethod.POST)
    public String modifyBasic(@RequestBody String data,  HttpServletRequest request, HttpServletResponse response) {
        //response.setHeader("content-type", "text/html;charset=UTF-8");
        JSONObject res = new JSONObject();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("Content-Type:application/json");
        JSONObject json = JSONObject.fromObject(data);
        String phone = json.getString("phone");
        String email = json.getString("email");
        String location = json.getString("location");
        String notice = json.getString("notice");
        ShopInfo shopinfo = new ShopInfo("中山大学第一饭堂", notice, "", "", phone, location, true);
        if(shopinfoService.modifyShopInfo(shopinfo)) {
            res.put("msg", "OK");
            JSONObject temp = new JSONObject();
            temp.put("phone", phone);
            temp.put("email", email);
            temp.put("location", location);
            temp.put("notice", notice);
            res.put("data", temp);
        } else {
            res.put("msg", "没有权限");
            res.put("data", null);
        }
        return res.toString();

    }

    @ResponseBody
    @CrossOrigin
    @RequestMapping(value = "/api/*/shop", method = RequestMethod.GET)
    public String modifyBasic(HttpServletRequest request, HttpServletResponse response) {
        //response.setHeader("content-type", "text/html;charset=UTF-8");
        JSONObject res = new JSONObject();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("Content-Type:application/json");
        ShopInfo shopinfo = shopinfoService.getShopInfo();
        if (shopinfo != null) {
            res.put("msg", "OK");
            JSONObject temp = new JSONObject();
            temp.put("name", shopinfo.getName());
            temp.put("info", shopinfo.getInfo());
            temp.put("icon_url", shopinfo.getIconUrl());
            temp.put("background_url", shopinfo.getBackgroundUrl());
            temp.put("telephone", shopinfo.getTelephone());
            temp.put("address", shopinfo.getAddress());
            temp.put("status", true);
            res.put("data",temp);
        } else {
            res.put("msg", "Forbbiden");
        }
        return res.toString();
    }

}


