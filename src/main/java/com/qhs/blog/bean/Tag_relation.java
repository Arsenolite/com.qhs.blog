package com.qhs.blog.bean;

/**
 * Created by QHS on 2017/5/25.
 */
public class Tag_relation {
    private int id;
    private int article;
    private int tag;

    public int getArticle() {
        return article;
    }

    public void setArticle(int article) {
        this.article = article;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
