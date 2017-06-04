package com.qhs.blog.controller;

import com.qhs.blog.bean.User;
import com.qhs.blog.serviceImpl.mailServiceImpl;
import com.qhs.blog.serviceImpl.userServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by QHS on 2017/5/27.
 */
@RestController
@RequestMapping(value = "/api/user")
public class userController {


    @Autowired
    private userServiceImpl userService;

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    public Map<String,Object> userReg(@RequestBody User user){

        Map<String, Object> result = userService.userReg(user);

        return result;
    }

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public Map<String,Object>  userLogin(@RequestBody User user){
        //登陆次数限制交给前端做（懒），反正有图片验证码挡着呢
        Map<String,Object> result = userService.userAuthc(user);
        return result;
    }

    //注销
    @RequestMapping(value = "/logout",method = RequestMethod.POST)
    public Map<String,Object> userLogout(@RequestParam (value = "token") String token){
        //
        Map<String,Object> result = userService.userLogout(user);
        return result;
    }

    //修改用户信息
    @RequestMapping(value = "/info/{id}" ,method = RequestMethod.POST)
    public Map<String,Object> userInfo(@RequestBody User user){

    }

}
