package com.qhs.blog.serviceImpl;

import com.qhs.blog.bean.Tag;
import com.qhs.blog.mapper.tagMapper;
import com.qhs.blog.service.tagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by QHS on 2017/6/5.
 */
@Service
public class tagServiceImpl implements tagService {
    @Autowired
    private tagMapper td;
    @Override
    public Map<String, Object> tagAdd(Tag tag) {
        Map<String, Object> result = new HashMap<>();
        //判断非空
        if(tag.getName()!=null){
            //判断是否存在
            if(td.checkExist(tag) != null){
                result.put("result","exist");
            }else{
                td.add(tag);
                result.put("result","success");
            }
        }else{
            result.put("result","incomplete");
        }

        return null;
    }

    @Override
    public Map<String, Object> tagDelete(Tag tag) {
        return null;
    }
}
