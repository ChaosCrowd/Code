package com.example.service.implement;

import com.example.dao.ICategoryDao;
import com.example.pojo.Category;
import com.example.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
        return categoryDao.getAllCategories();
    }

    @Override
    public Category getCategoryById(int id) {
        return categoryDao.getCategoryByID(id);
    }

    @Override
    public boolean addCategory(Category category) {
        if (categoryDao.insertCategory(category.getName())> 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean deleteCategoryById(int id) {
        if (categoryDao.deleteCategoryById(id) > 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean modifyCategory(Category category) {
        if (categoryDao.updateCategory(category) > 0) {
            return true;
        } else {
            return false;
        }
    }
}
