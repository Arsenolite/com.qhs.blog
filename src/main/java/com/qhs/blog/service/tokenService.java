package com.qhs.blog.service;

import java.util.Map;

/**
 * Created by QHS on 2017/5/29.
 */
public interface tokenService {
    public String createToken(Map<String,Object> map);

    public Map<String,Object> validToken(String token);
}
