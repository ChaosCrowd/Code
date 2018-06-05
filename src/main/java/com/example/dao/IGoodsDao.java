package com.example.dao;

import com.example.pojo.Goods;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import java.util.List;


/**
 * 商品DAO接口
 * 无需实现类，通过mybatis的GoodsMapper.xml实现
 */
@Component
public interface IGoodsDao {

    /**
     * 获取所有商品
     * @return 商品列表
     */
    public List<Goods> getAllGoods();

    // 废弃方法（category已定义成新类型）
    // public List<String> getAllCategories();


    /**
     * 通过id获取Goods
     * @param id 商品id
     * @return 对应Goods实例 or null
     */
    public Goods getGoodsById(int id);

    /**
     * 插入商品
     * @param goods
     * @return 数据库变更行数
     * @throws DataAccessException 插入失败，抛出异常
     */
    public int insertGoods(Goods goods) throws DataAccessException;

    /**
     * 通过商品id删除对应商品
     * @param id
     * @return 数据库变更行数
     * @throws DataAccessException
     */
    public int deleteGoodsById(int id) throws DataAccessException;

    /**
     * 更新商品
     * @param goods
     * @return 数据库变更行数
     * @throws DataAccessException
     */
    public int updateGoods(Goods goods) throws DataAccessException;

    /**
     * 获取某类型下所有商品
     * @param cate 指定类型
     * @return 商品列表
     */
    public List<Goods> getGoodsListByCategory(String cate);

    /**
     * 通过商品名获取商品
     * 考虑到可能出现的同名商品情况
     * 尽量使用id查询
     * @param name
     * @return 商品列表
     */
    public List<Goods> getGoodsListByName(String name);
}
