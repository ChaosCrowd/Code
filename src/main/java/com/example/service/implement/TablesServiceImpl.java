package com.example.service.implement;

import com.example.dao.ITablesDao;
import com.example.pojo.Tables;
import com.example.service.ITablesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 桌位Service实现类
 */
@Service
public class TablesServiceImpl implements ITablesService {

    /**
     * 自动注入的dao
     */
    @Autowired
    private ITablesDao tablesDao;

    @Override
    public List<Tables> getTablesList() {
        return null;
    }

    @Override
    public List<Tables> getTablesListByStatus(int status) {
        return null;
    }

    @Override
    public Tables getTablesByNumber(int number) {
        return null;
    }

    @Override
    public boolean addTables(Tables tables) {
        return false;
    }

    @Override
    public boolean deleteTableByNumber(int number) {
        return false;
    }

    @Override
    public boolean modifyTables(Tables tables) {
        return false;
    }
}
