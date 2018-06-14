package com.example.service.implement;

import com.example.dao.ICategoryDao;
import com.example.pojo.Category;
import com.example.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public ArrayList<Category> getCategoriesList() {
        return categoryDao.getAllCategories();
    }

    @Override
    public Category getCategoryById(int id) {
        return categoryDao.getCategoryByID(id);
    }

    @Override
    public boolean addCategory(Category category) {
        try {
            if (categoryDao.insertCategory(category.getName()) > 0) {
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
    public boolean deleteCategoryById(int id) {
        try {
            if (categoryDao.deleteCategoryById(id) > 0) {
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
    public boolean modifyCategory(Category category) {
        try {
            if (categoryDao.updateCategory(category) > 0) {
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
