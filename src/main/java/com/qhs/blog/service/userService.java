package com.qhs.blog.service;

import com.qhs.blog.bean.User;

import java.util.Map;

/**
 * Created by QHS on 2017/5/27.
 */
public interface userService {

    public Map<String , Object> userReg(User user);

    public Map<String, Object> userAuthc(User user);

    public Map<String , Object> userEdit(User user);

    public Map<String , Object> userRepeat(User user);

    public Map<String , Object> userLogout(String token);

    public Map<String, Object> userInfo(Integer uid);
}