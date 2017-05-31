package com.qhs.blog.controller;

import com.qhs.blog.bean.User;
import com.qhs.blog.serviceImpl.mailServiceImpl;
import com.qhs.blog.serviceImpl.tokenServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by QHS on 2017/5/30.
 */
@RestController
@RequestMapping(value = "/api/mail")
public class mailController {
    @Autowired
    private mailServiceImpl mailService;

    @Autowired
    private tokenServiceImpl tokenService;


    @RequestMapping(value = "/send", method = RequestMethod.POST)
    public Map<String, Object> sendMail(@RequestBody User user) {

        Map<String, Object> result = new HashMap<>();

        Date date = new Date();
        Map<String, Object> payload = new HashMap<>();
        payload.put("value", "getMailCode");//表明操作
        payload.put("iat", date.getTime());//签发时间是服务器当前时间
        payload.put("ext", date.getTime() + 1000 * 60 * 60);//过期时间一小时

        String token = tokenService.createToken(payload);

        mailService.genMailByUser(user, token);
        result.put("sent", user.getEmail());
        result.put("token", token);
        return result;
    }


    @RequestMapping(value = "/valid", method = RequestMethod.GET)
    public Map<String, Object> findMail(@RequestParam String token, @RequestParam String reqMailCode) {
        Assert.hasText(token);
        Assert.hasText(reqMailCode);
        String mailCode = mailService.findMailCode(token);
        Map<String, Object> params = new HashMap<>();
        boolean isValid = false;
        if (reqMailCode.equals(mailCode)) {
            isValid = true;
            //发一个新的token给客户端
            Date date = new Date();
            Map<String, Object> payload = new HashMap<>();
            payload.put("value", "mailAuthed");//表明操作
            payload.put("iat", date.getTime());//签发时间是服务器当前时间
            payload.put("ext", date.getTime() + 1000 * 60 * 60);//过期时间60分钟
            token = tokenService.createToken(payload);
            //将新token和是否有效一起装进去
            params.put("valid", isValid);
            params.put("token",token);
        }
        params.put("valid", isValid);
        return params;
    }

}
