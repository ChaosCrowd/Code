package com.example.service.implement;

import com.example.dao.ICategoryDao;
import com.example.pojo.Category;
import com.example.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 类别管理service实现类
 */
@Service
public class CategoryServiceImpl implements ICategoryService {

    /**
     * 自动注入的DAO
     */
    @Autowired
    private ICategoryDao categoryDao;

    @Override
    public List<Category> getCategoriesList() {
        return null;
    }

    @Override
    public Category getCategoryById(int id) {
        return null;
    }

    @Override
    public boolean addCategory(Category category) {
        return false;
    }

    @Override
    public boolean deleteCategoryById(int id) {
        return false;
    }

    @Override
    public boolean modifyCategory(Category category) {
        return false;
    }
}
