package com.qhs.blog.serviceImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qhs.blog.dao.redisDao;
import com.qhs.blog.util.lianggzone.captcha.component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.image.BufferedImage;
import java.util.Map;

import static java.util.concurrent.TimeUnit.MINUTES;


/**
 * <h3>概要:</h3><p>CaptchaService</p>
 * <h3>功能:</h3><p>CaptchaService</p>
 * <h3>履历:</h3>
 * <li>2017年1月5日  v0.1 版本内容: 新建</li>
 *
 * @author 粱桂钊
 * @since 0.1
 */
@Service
public class captchaServiceImpl{

    @Autowired
    private component captchaComponent;

    @Autowired
    protected redisDao redisDao;


//    生成验证码的方法负责将token和生成的验证码字符串存入redis，把图片返回Controller层
//    接下来邮件验证码也继承这个思路
//    Token生成由captchaController完成
//    @Autowired
//    private tokenServiceImpl tokenService;

    public BufferedImage genCaptcha(String token) {
        //问component要一个包含了验证码字符串、图片的map
        Map<String, Object> captcha = captchaComponent.genCaptcha();
        //拿到值
        String code = (String) captcha.get("code");
        BufferedImage img = (BufferedImage) captcha.get("img");
        //把token和验证码存进去
        redisDao.addValue(token, code);
        //指定这份关联10分钟后过期
        redisDao.expire(token, 10,MINUTES);
        //把图片往上层丢
        return img;
    }

    /**
     * 查询图片验证码
     *
     * @param token
     * @return
     */
    public String findCaptcha(String token) {
        return redisDao.getValue(token);
    }

}