package com.qhs.blog.controller;

import com.qhs.blog.bean.User;
import com.qhs.blog.serviceImpl.mailServiceImpl;
import com.qhs.blog.serviceImpl.userServiceImpl;
import com.sun.org.apache.regexp.internal.RE;
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
        //将Token交给Service让它处理
        Map<String,Object> result = userService.userLogout(token);
        return result;
    }

    //修改用户信息
    //构建完URL之后再搞拦截器
    @RequestMapping(value = "/info" ,method = RequestMethod.POST)
    public Map<String,Object> userEdit(@RequestBody User user,@RequestParam("uid") Integer uid){
        //将id封装进user对象
        user.setId(uid);
        Map<String,Object> result = userService.userEdit(user);
        return result;
    }

    //获取用户信息
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public Map<String,Object> userInfo(@RequestParam("uid") Integer uid){
        Map<String,Object> result = userService.userInfo(uid);
        return result;
    }

    //修改密码
    @RequestMapping(value = "/editPwd", method = RequestMethod.PUT)
    public Map<String,Object> editPwd(@RequestBody User user){
        Map<String,Object> result = userService.editPwd(user);
        return result;
    }
}
