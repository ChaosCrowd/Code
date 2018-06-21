package com.example.controller;

import com.alibaba.fastjson.JSON;
import com.example.pojo.Manager;
import com.example.pojo.responseObj;
import com.example.service.IManagerService;
import com.example.utils.EncryptUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录、注册controller
 */
@Controller
@RequestMapping("/api/*/")
public class RegisterController {
    @Autowired
    private IManagerService managerService;

    private static final Logger logger = Logger.getLogger(RegisterController.class);

    @ResponseBody
    @RequestMapping(value="signin", method = RequestMethod.POST)
    public String signIn(HttpServletRequest request, HttpServletResponse response){
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("Content-Type:application/json");
        response.setStatus(200);
        logger.debug("sign in: " + username + " " + password);

        // password为null时，加密方法会出错
        if (null == password) {
            password = "null";
        }
        Manager man = new Manager(username, EncryptUtils.encrypt(password));

        responseObj obj;
        // 登录验证通过
        if (managerService.verifyPassword(man)) {
            logger.debug("login succeed.");
            obj = new responseObj("OK", JSON.toJSONString(man));
            return JSON.toJSONString(obj);
        } else {    // 验证失败
            logger.debug("login failed.");
            obj = new responseObj("ERROR", "username or password is wrong");
            return JSON.toJSONString(obj);
        }
    }

    @ResponseBody
    @RequestMapping(value = "signup", method = RequestMethod.POST)
    public String  signUp(HttpServletRequest request, HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("Content-Type:application/json");
        response.setStatus(200);
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        logger.debug("sign up: " + username + " " + password);

        if (null == username || username.length() < 4 || username.length() > 16) {
            return JSON.toJSONString(new responseObj("ERROR", "wrong format with username"));
        }
        if (null == password || password.length() < 6 || password.length() > 16) {
            return JSON.toJSONString(new responseObj("ERROR", "wrong format with password"));
        }
        if (managerService.isUsernameExist(username)) {
            return JSON.toJSONString(new responseObj("ERROR", "exists username"));
        }
        // 添加用户
        managerService.addUser(new Manager(username, EncryptUtils.encrypt(password)));
        logger.debug("add user: " + username);

        // wrong
        return JSON.toJSONString(new responseObj("OK", username));
    }
}
