package com.example.interceptor;


import com.auth0.jwt.interfaces.Claim;
import com.example.service.IManagerService;
import com.example.utils.JwtTokenUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Access Token 拦截器
 * 验证Api权限
 */
@Component
public class VerifyInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = Logger.getLogger(VerifyInterceptor.class);

    @Autowired
    private IManagerService managerService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.debug("Access token executing...");
        //System.out.println(("Access!"));
        //response.setStatus(200);
        //System.out.println(request.getMethod());

        /*response.addHeader("Access-Control-Allow-Origin","*");
        response.addHeader("Access-Control-Allow-Credentials", "true");
        response.addHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE, PUT, HEAD");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type");*/
        response.addHeader("Access-Control-Max-Age", "360000");
        //response.setHeader("Access-Control-Allow-Headers", "If-Modified-Since");
        response.setHeader("Access-Control-Allow-Origin", "http://139.199.71.21:8080");
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Allow-Methods", "*");
        response.setHeader("Access-Control-Allow-Headers", "Origin, X-Requested-With, Content-Type, Accept");
        response.setHeader("Access-Control-Expose-Headers", "*");

        /*if (request.getMethod().equals("OPTIONS")) {
            HttpUtil.setResponse(response, HttpStatus.OK.value(), null);
            return true;
        }*/

        if (request.getMethod().equals("OPTIONS")) {
            /*System.out.println("111111");*/
            response.setStatus(200);
            //System.out.println(response.getStatus());
            return true;
        }
        // 标记，用于最后返回值
        boolean flag = false;

        // 从请求报文cookies中获取token
        String token = null;

        Cookie[] cookies = request.getCookies();
        // 结果非空时，从数组中查找名为token的cookie
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        // 开发时无需token，测试or部署时需要改成false
        if (null == token || "".equals(token)) {
            System.out.println("empty token");
            flag = false;
        } else {
            try {
                Map<String, Claim> map = JwtTokenUtils.vertifyToken(token);
                String name = map.get("username").asString();
                flag = managerService.isUsernameExist(name);
            } catch (RuntimeException e) {
                e.printStackTrace();
                //System.out.println("权限认证失败");
                logger.debug("权限认证失败");
            }
        }
        if (!flag) {    // 验证失败时设置response错误码
            response.setStatus(401);
            //System.out.println("ttttt");

        }
        //System.out.println("222: "+response.getStatus());
        return flag;
    }
}
