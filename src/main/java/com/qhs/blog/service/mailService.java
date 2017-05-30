package com.qhs.blog.service;

import com.qhs.blog.bean.User;

/**
 * Created by QHS on 2017/5/28.
 */
public interface mailService {
//    创建邮件的方法：
//    参数为一个用户bean
//    方法内部逻辑为解包出用户名和邮箱
//    将用户名填充进邮件模板，生成随机验证码
//    调用send方法发送，并且返回生成的验证码

    public void sendMail(String target,String mailCode);
    public void genMailByUser(User user,String token);
    public String genMailCode();

}
