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
        try {
            if (goodsDao.insertGoods(goods) > 0) {
                for (Category c : goods.getCate()) {
                    goodsDao.insertSpecificRelation(goods.getId(), c.getId());
                }
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean deleteGoodsById(int id) {
        Goods goods = goodsDao.getGoodsById(id);
        try {
            if (goodsDao.deleteGoodsById(id) > 0) {
                for (Category c : goods.getCate()) {
                    goodsDao.deleteSpecficRelation(goods.getId(), c.getId());
                }
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public boolean modifyGoods(Goods goods) {
        int id = goods.getId();
        Goods previousGoods = goodsDao.getGoodsById(id);
        try {
            if (goodsDao.updateGoods(goods) > 0) {
                for (Category c : previousGoods.getCate()) {
                    goodsDao.deleteSpecficRelation(previousGoods.getId(), c.getId());
                }
                for (Category c : goods.getCate()) {
                    goodsDao.insertSpecificRelation(goods.getId(), c.getId());
                }
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }
}
