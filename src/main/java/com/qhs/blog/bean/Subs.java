package com.qhs.blog.bean;

/**
 * Created by QHS on 2017/5/25.
 */
public class Subs {
    private int id;
    private int target;
    private String action_type;
    private int user;
    private String create_at;
    private String target_type;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTarget() {
        return target;
    }

    public void setTarget(int target) {
        this.target = target;
    }

    public String getAction_type() {
        return action_type;
    }

    public void setAction_type(String action_type) {
        this.action_type = action_type;
    }

    public int getUser() {
        return user;
    }

    public void setUser(int user) {
        this.user = user;
    }

    public String getCreate_at() {
        return create_at;
    }

    public void setCreate_at(String create_at) {
        this.create_at = create_at;
    }

    public String getTarget_type() {
        return target_type;
    }

    public void setTarget_type(String target_type) {
        this.target_type = target_type;
    }
}
