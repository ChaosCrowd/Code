package com.example.service;

import com.example.service.implement.CategoryServiceImpl;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/spring-mybatis_test.xml"})
public class ICategoryServiceTest {

    @Autowired
    private ICategoryService service;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void getCategoriesList() {
    }

    @Test
    public void getCategoryById() {
        assertEquals(1, service.getCategoryById(1));
    }

    @Test
    public void addCategory() {
    }

    @Test
    public void deleteCategoryById() {
    }

    @Test
    public void modifyCategory() {
    }
}