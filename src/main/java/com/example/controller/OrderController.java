package com.example.controller;

import com.example.pojo.Goods;
import com.example.pojo.Order;
import com.example.service.IMenuService;
import com.example.service.IOrderService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class OrderController {
    @Autowired
    private IOrderService orderService;

    @Autowired
    private IMenuService menuService;

    @RequestMapping(value = "/api/*/order", method = RequestMethod.POST)
    @ResponseBody
    public String uploadOrder(@RequestBody String data, HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = new JSONObject();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("Content-Type:application/json");

        JSONObject json = JSONObject.fromObject(data);
        float totleprice = 0;
        //获取桌号
        String temp = json.getString("tables_number");

        //获取时间
        String temptimestamp = json.getString("timestamp");
        Timestamp ts = new Timestamp(Long.parseLong(temptimestamp));
        Date date = ts;
        java.sql.Date sqlDate=new java.sql.Date(date.getTime());
        int tablenum = Integer.parseInt(temp);

        //JSON数组
        JSONArray order = json.getJSONArray("order");
        ArrayList<Integer> goodlistid = new ArrayList<Integer>();
        ArrayList<Integer> countid = new ArrayList<Integer>();
        for (int i = 0; i < order.size(); i++) {
            JSONObject temp1 = JSONObject.fromObject(order.get(i));
            int id = Integer.parseInt(temp1.getString("goods_id"));
            goodlistid.add(id);
            int count = Integer.parseInt(temp1.getString("count"));
            countid.add(count);

            Goods temp2 = menuService.getGoodsById(id);

            //价格计算
            float price = temp2.getPrice();
            totleprice += price*count;
        }


        Order temp3 = new Order(sqlDate, goodlistid, countid, totleprice, 2, tablenum);
        System.out.println(temp3.toString());
        System.out.println(orderService.addOrder(temp3));
        res.put("msg", "OK");
        return res.toString();

    }

}

