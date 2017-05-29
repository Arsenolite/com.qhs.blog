package com.qhs.blog.controller;

import com.qhs.blog.serviceImpl.captchaServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by QHS on 2017/5/28.
 */
@RestController
@RequestMapping(value = "/api/v0.1/captcha")
public class captchaController {
    @Autowired
    private captchaServiceImpl captchaService;

    /**
     * 生成图片验证码
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public void genCaptcha(HttpServletRequest req, HttpServletResponse resp) {
        String token = req.getParameter("token");
        // TODO:有效性检验优化
        Assert.hasText(token);
        captchaService.genCaptcha(token, req, resp);
    }

    /**
     * 查询图片验证码
     */
    @RequestMapping(value = "/valid", method = RequestMethod.GET)
    public Map<String, Object> findCaptcha(HttpServletRequest req, HttpServletResponse resp) {
        String token = req.getParameter("token");
        String reqCaptchaCode = req.getParameter("captchaCode");
        // TODO:有效性检验优化
        Assert.hasText(token);
        Assert.hasText(reqCaptchaCode);
        String captchaCode = captchaService.findCaptcha(token);

        Map<String, Object> params = new HashMap<String, Object>();
        boolean isValid = false;
        if(reqCaptchaCode.equals(captchaCode)){
            isValid = true;
        }
        params.put("valid", isValid);
        return params;
    }
}