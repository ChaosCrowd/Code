package com.example.interceptor;


import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * Access Token 拦截器
 * 验证Api权限
 */
@Component
public class VerifyInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = Logger.getLogger(VerifyInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("Access token executing...");

        // 从请求报文中获取token
        String token = request.getParameter("token");

        // 开发时无需token，部署到服务器时需要改成false
        if (null == token || "".equals(token)) {
            return true;
        }
        request.getParameterNames();

        return false;
    }
}
