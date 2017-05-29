package com.qhs.blog.controller;

import com.qhs.blog.bean.User;
import com.qhs.blog.serviceImpl.mailServiceImpl;
import com.qhs.blog.serviceImpl.userServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by QHS on 2017/5/27.
 */
@RestController
@RequestMapping(value = "/api/user")
public class userController {
    @Autowired
    private mailServiceImpl mailService;

//    @RequestMapping(value = "/send")
//    public void testSendMail(){
//        User user = new User();
//        user.setEmail("1581715021@qq.com");
//        user.setName("芸");
//        mailService.genMailByUser(user);
//    }
    @Autowired
    private userServiceImpl userService;


}
