package com.example.controller;

import com.example.pojo.Goods;
import com.example.pojo.Order;
import com.example.service.IMenuService;
import com.example.service.IOrderService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
        int tablenum = Integer.parseInt(temp);

        //获取时间
        String temptimestamp = json.getString("timestamp");
        Timestamp ts = new Timestamp(Long.parseLong(temptimestamp));
        Date date = ts;
        java.sql.Date sqlDate=new java.sql.Date(date.getTime());


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
        //System.out.println(temp3.toString());
        orderService.addOrder(temp3);
        res.put("msg", "OK");
        return res.toString();

    }


    @RequestMapping(value = "/api/*/query/order", method = RequestMethod.GET)
    @ResponseBody
    public String getOrder(@RequestParam(value = "orderState", defaultValue = "-1") String state, HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = new JSONObject();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("Content-Type:application/json");

        //状态string转int
        //System.out.println(state);
        int stateid = 2;
        if (state == "unaccepted") {
            stateid = 2;
        } else if (state == "accepted") {
            stateid = 3;
        } else if (state == "finished") {
            stateid = 4;
        } else if (state == "refused") {
            stateid = 5;
        }
        List<Order> orderlist = orderService.getOrdersListByStatus(stateid);
        if (orderlist.size() != 0) {
            response.setStatus(200);
            res.put("msg", "OK");
            JSONArray json = new JSONArray();

            for (int i = 0; i < orderlist.size(); i++) {
                JSONObject temp = new JSONObject();
                int id = orderlist.get(i).getId();
                int tableid = orderlist.get(i).getTablesNumber();
                //时间
                Date date = orderlist.get(i).getAddDate();
                long t = date.getTime();
                List<Integer> goodsidlist = orderlist.get(i).getGoodsId();
                List<Integer> cuntlist = orderlist.get(i).getGoodsCount();
                float price = orderlist.get(i).getPrice();

                temp.put("orderID", id);

                temp.put("orderState", state);

                temp.put("tableID", tableid);
                temp.put("time", t);
                temp.put("totlePrice", price);
                JSONArray temp1 = new JSONArray();
                for (int j = 0; j < goodsidlist.size(); j++) {
                    JSONObject temp2 = new JSONObject();
                    temp2.put("dishID", goodsidlist.get(j));
                    temp2.put("dishNum", cuntlist.get(j));
                    temp1.add(temp2);
                }
                temp.put("orderContent", temp1);
                json.add(temp);
            }
            res.put("data", json);
        }
        return res.toString();
    }

    //接受订单
    @RequestMapping(value = "/api/*/accept/order", method = RequestMethod.GET)
    @ResponseBody
    public String acceptOrder(@RequestParam(value = "orderID", defaultValue = "-1") int orderid, HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = new JSONObject();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("Content-Type:application/json");

        Order order = orderService.getOrderById(orderid);
        order.setStatus(3);
        if (orderService.modifyOrder(order)) {
            res.put("msg", "OK");
            JSONObject temp = new JSONObject();
            temp.put("orderID", order.getId());
            res.put("data", temp);
        }
        return res.toString();
    }

    //拒绝订单
    @RequestMapping(value = "/api/*/refuse/order", method = RequestMethod.GET)
    @ResponseBody
    public String refuseOrder(@RequestParam(value = "orderID", defaultValue = "-1") int orderid, HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = new JSONObject();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("Content-Type:application/json");

        Order order = orderService.getOrderById(orderid);
        order.setStatus(5);
        if (orderService.modifyOrder(order)) {
            res.put("msg", "OK");
            JSONObject temp = new JSONObject();
            temp.put("orderID", order.getId());
            res.put("data", temp);
        }
        return res.toString();
    }

    //完成订单
    @RequestMapping(value = "/api/*/finish/order", method = RequestMethod.GET)
    @ResponseBody
    public String finishOrder(@RequestParam(value = "orderID", defaultValue = "-1") int orderid, HttpServletRequest request, HttpServletResponse response) {
        JSONObject res = new JSONObject();
        response.setCharacterEncoding("UTF-8");
        response.setContentType("Content-Type:application/json");

        Order order = orderService.getOrderById(orderid);
        order.setStatus(4);
        if (orderService.modifyOrder(order)) {
            res.put("msg", "OK");
            JSONObject temp = new JSONObject();
            temp.put("orderID", order.getId());
            res.put("data", temp);
        }
        return res.toString();
    }

}

