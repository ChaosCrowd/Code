package com.example.service.implement;

import com.example.dao.IGoodsDao;
import com.example.pojo.Goods;
import com.example.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品操作Service实现类
 */
@Service
public class MenuServiceImpl implements IMenuService {

    /**
     * 自动注入的DAO
     */
    @Autowired
    private IGoodsDao goodsDao;

    @Override
    public List<Goods> getGoodsList() {
        return null;
    }

    @Override
    public List<String> getCategoriesList() {
        return null;
    }

    @Override
    public List<Goods> getGoodsListByCategory(String category) {
        return null;
    }

    @Override
    public Goods getGoodsById(int id) {
        return null;
    }

    @Override
    public boolean addGoods(Goods goods) {
        return false;
    }

    @Override
    public boolean deleteGoodsById(int id) {
        return false;
    }

    @Override
    public boolean modifyGoods(Goods goods) {
        return false;
    }
}
