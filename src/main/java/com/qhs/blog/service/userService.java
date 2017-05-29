package com.qhs.blog.service;

import com.qhs.blog.bean.User;

/**
 * Created by QHS on 2017/5/27.
 */
public interface userService {
//    第一个方法：用户注册相关逻辑
//    思路：
//    1.前端送来json，包含一个User的bean。
//    2.前端负责：
//    页面内验证这个bean是否信息齐全，
//    密码是否符合规范，
//    ajax验证bean中包含的用户名是否重复，
//    在点击发送邮件验证码时检测邮箱是否重复
//    3.后端负责：
//    构建验证码
//    将bean存入数据库
//    返回对验证码的验证
    public boolean userReg(User user);

    public boolean userLogin(User user);

    public boolean userLogout(User user);

    public boolean userEdit();


}
