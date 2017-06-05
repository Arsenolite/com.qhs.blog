package com.qhs.blog.service;

import com.qhs.blog.bean.Tag;
import com.qhs.blog.bean.Tag_relation;

import java.util.Map;

/**
 * Created by QHS on 2017/6/5.
 */
public interface tagService {
    public Map<String , Object> tagAdd(Tag tag);
    public Map<String , Object> tagDelete(Tag tag);
}
