package com.example.service.implement;

import com.example.dao.ICategoryDao;
import com.example.dao.IGoodsDao;
import com.example.pojo.Category;
import com.example.pojo.Goods;
import com.example.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
        return goodsDao.getAllGoods();
    }

    @Override
    public List<Goods> getGoodsListByCategory(Category category) {
        return goodsDao.getGoodsListByCategory(category);
    }

    @Override
    public Goods getGoodsById(int id) {
        return goodsDao.getGoodsById(id);
    }

    @Override
    public boolean addGoods(Goods goods) {
        if(goodsDao.insertGoods(goods) > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteGoodsById(int id) {
        if (goodsDao.deleteGoodsById(id) > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean modifyGoods(Goods goods) {
        if (goodsDao.updateGoods(goods) > 0) {
            return true;
        } else {
            return false;
        }
    }
}
