package com.qhs.blog.controller;

import com.qhs.blog.bean.User;
import com.qhs.blog.serviceImpl.mailServiceImpl;
import com.qhs.blog.serviceImpl.userServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

}
