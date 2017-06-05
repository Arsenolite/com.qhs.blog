package com.qhs.blog.mapper;

import com.qhs.blog.bean.Tag;
import org.apache.ibatis.annotations.Param;

/**
 * Created by QHS on 2017/6/5.
 */
public interface tagMapper {
    //增加一个Tag
    public int add(@Param("tag") Tag tag);
    //增加之前查询是否存在
    public Tag checkExist(@Param("tag") Tag tag);
    //提供接口删除没用的Tag
    public int delete(@Param("tag") Tag tag);

}
