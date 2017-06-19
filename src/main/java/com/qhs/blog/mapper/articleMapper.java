package com.qhs.blog.mapper;

import com.qhs.blog.bean.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by QHS on 2017/6/16.
 */
public interface articleMapper {
    //精确获取文章内容
    public Article getArticle(@Param("id") Integer id);
    //修改文章内容
    public int updateArticle(@Param("article") Article article);
    //增加一篇文章
    public int addArticle(@Param("article") Article article);
    //分页获取指定级别的文章
    public List<Article> listArticle(@Param("level") Integer level,@Param("start") int start, @Param("count") int count);
    //分页根据作者id获取文章

    //删除文章

}
