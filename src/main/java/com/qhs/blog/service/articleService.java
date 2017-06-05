package com.qhs.blog.service;

import com.qhs.blog.bean.Article;

import java.util.Map;

/**
 * Created by QHS on 2017/6/5.
 */
public interface articleService {
    public Map<String, Object> addArticle(Article article);

    public Map<String, Object> editArticle(Article article);

    public Map<String, Object> deleteArticle(Article article);

    public Map<String, Object> listArticle(Integer level);

}
