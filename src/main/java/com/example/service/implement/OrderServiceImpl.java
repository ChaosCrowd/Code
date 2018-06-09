package com.example.service.implement;

import com.example.dao.IOrderDao;
import com.example.pojo.Order;
import com.example.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;

/**
 * 订单Service实现类
 */
@Service
public class OrderServiceImpl implements IOrderService {

    /**
     * 自动注入的DAO接口
     */
    //@Autowired
    private IOrderDao orderDao;

    @Override
    public List<Order> getOrdersList() {
        return null;
    }

    @Override
    public List<Order> getOrdersListByTimeSlot(Date begin, Date end) {
        return null;
    }

    @Override
    public List<Order> getOrdersListByStatus(int status) {
        return null;
    }

    @Override
    public Order getOrderById(int id) {
        return null;
    }

    @Override
    public boolean addOrder(Order order) {
        return false;
    }

    @Override
    public boolean deleteOrderById(int id) {
        return false;
    }

    @Override
    public boolean modifyOrder(Order order) {
        return false;
    }
}
