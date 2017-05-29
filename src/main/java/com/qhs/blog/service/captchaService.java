package com.qhs.blog.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by QHS on 2017/5/28.
 */
public interface captchaService {
    //获取图片验证码
    public void genCaptcha(String token, HttpServletRequest req, HttpServletResponse resp);
    //验证
    public String findCaptcha(String token);
}

