package com.qhs.blog.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 配置权限管理用到的页面。Filter中如果登陆失败会跳转到这里的页面。
 */
@RestController
@RequestMapping(value = "/api/index")
public class indexController {
    @RequestMapping(value = "/200", method = RequestMethod.GET)
    public Map<String ,Object> ok(){
        Map<String, Object> result = new HashMap<>();
        result.put("msg","OK");
        return result;
    }


    @RequestMapping(value = "/401", method = RequestMethod.GET)
    public Map<String ,Object> unauthorized(HttpServletResponse resp){
        Map<String, Object> result = new HashMap<>();
        result.put("auth","Unauthorized");
        resp.setStatus(401);//设定返回状态码
        return result;
    }

    @RequestMapping(value = "/404", method = RequestMethod.GET)
    public Map<String ,Object> notFound(HttpServletResponse resp){
        Map<String, Object> result = new HashMap<>();
        result.put("msg","Not Found");
        resp.setStatus(404);
        return result;
    }

    @RequestMapping(value = "/415", method = RequestMethod.GET)
    public Map<String ,Object> unsupportedMediaType(HttpServletResponse resp){
        Map<String, Object> result = new HashMap<>();
        result.put("msg","Unsupported Media Type");
        resp.setStatus(415);
        return result;
    }


    @RequestMapping(value = "/500", method = RequestMethod.GET)
    public Map<String ,Object> internalServerError(HttpServletResponse resp){
        Map<String, Object> result = new HashMap<>();
        result.put("msg","Internal Server Error");
        resp.setStatus(500);
        return result;
    }



}
