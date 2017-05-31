package com.qhs.blog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 配置权限管理用到的页面
 */
@RestController
@RequestMapping(value = "/api/index")
public class indexController {
    @RequestMapping(value = "/200", method = RequestMethod.GET)
    public Map<String ,Object> success(){
        Map<String, Object> result = new HashMap<>();
        result.put("auth","success");
        return result;
    }

    @RequestMapping(value = "/unauthorized", method = RequestMethod.GET)
    public Map<String ,Object> authFailed(HttpServletResponse resp){
        Map<String, Object> result = new HashMap<>();
        result.put("auth","Insufficient Permissions");
        resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);//设定返回状态码
        return result;
    }



}
