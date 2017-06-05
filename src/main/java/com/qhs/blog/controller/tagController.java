package com.qhs.blog.controller;

import com.qhs.blog.bean.Tag;
import com.qhs.blog.serviceImpl.tagServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 终于摆脱用户这块了，开始写别的功能。。2017-6-5 15:22:47
 */
@RestController
@RequestMapping(value = "/api/tag")
public class tagController {

    @Autowired
    private tagServiceImpl tagService;

    //增加一条tag
    @RequestMapping(value = "/add")
    public Map<String ,Object> addTag(@RequestBody Tag tag){
        Map<String, Object> result = tagService.tagAdd(tag);
        return result;
    }
    //删除tag(不提供)

}
