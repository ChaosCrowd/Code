package com.example.service;

import com.example.pojo.Category;

import java.util.List;

/**
 * 商品类别操作Service接口
 */
public interface ICategoryService {

    /**
     * 获取所有商品类别
     * @return 类别列表
     */
    public List<Category> getCategoriesList();

    /**
     * 查
     * @param id
     * @return
     */
    public Category getCategoryById(int id);

    /**
     * 增
     * @param category
     * @return 操作结果
     */
    public boolean addCategory(Category category);

    /**
     * 删
     * @param id
     * @return 操作结果
     */
    public boolean deleteCategoryById(int id);

    /**
     * 改
     * @param category
     * @return 操作结果
     */
    public boolean modifyCategory(Category category);
}
