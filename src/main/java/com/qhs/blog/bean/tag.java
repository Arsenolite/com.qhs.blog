package com.qhs.blog.bean;

/**
 * Created by QHS on 2017/5/25.
 */
public class tag {
    private int id;
    private String name;
    private int parent;

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
