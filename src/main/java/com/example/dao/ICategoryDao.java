package com.example.dao;

import com.example.pojo.Category;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.util.ArrayList;

/**
 * 商品类别DAO接口
 * 通过CategoryMapper实现
 */
@Component
public interface ICategoryDao {
    /**
     * 通过id获取类别
     * @param id
     * @return
     */
    public Category getCategoryByID(int id);

    /**
     * 获取所有类别
     * @return
     */
    public ArrayList<Category> getAllCategories();

    /**
     * 插入新类别
     * 只传入name，id自动生成
     * @param name 类别名
     * @return 操作影响的行数
     * @throws DataAccessException 插入失败抛出异常
     */
    public int insertCategory(String name) throws DataAccessException;

    /**
     * 通过id删除对应分类
     * @param id
     * @return 影响的行数
     * @throws DataAccessException 删除失败 抛出异常
     */
    public int deleteCategoryById(int id) throws DataAccessException;

    /**
     * 更新分类
     * @param category
     * @return 影响行数
     * @throws DataAccessException
     */
    public int updateCategory(Category category) throws DataAccessException;
}
