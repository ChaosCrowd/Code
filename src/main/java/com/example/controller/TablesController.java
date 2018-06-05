package com.example.controller;

import com.example.service.ITablesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/api/tables")
public class TablesController {
    @Autowired
    private ITablesService tablesService;

}
