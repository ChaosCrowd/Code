package com.example.service.implement;

import com.example.dao.IManagerDao;
import com.example.pojo.Manager;
import com.example.service.IManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * admin管理Service实现类
 */
@Service
public class ManagerServiceImpl implements IManagerService {
    /**
     * 自动注入的DAO
     */
    @Autowired
    private IManagerDao managerDao;


    @Override
    public boolean verifyPassword(Manager manager) {
        return false;
    }

    @Override
    public boolean addUser(Manager manager) {
        return false;
    }

    @Override
    public boolean isUsernameExist(String username) {
        return false;
    }

    @Override
    public boolean changePassword(Manager manager) {
        return false;
    }

    @Override
    public boolean deleteUserByName(String username) {
        return false;
    }
}
