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

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/api/order", method = RequestMethod.POST)
public class OrderController {
    @Autowired
    private IOrderService orderService;

    @Autowired
    private IMenuService menuService;

    public void saveOrder(@RequestBody JSONObject data) {
        float totleprice = 0;
        String temp = data.optString("tables_number");
        int tablenum = Integer.parseInt(temp);

        //JSON数组
        JSONArray order = data.getJSONArray("order");
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
        //获取时间
        java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());
        Order temp3 = new Order(currentDate, goodlistid, countid, totleprice, 2, tablenum);
        orderService.addOrder(temp3);

    }

}

