package com.qhs.blog.serviceImpl;

import com.qhs.blog.bean.User;
import com.qhs.blog.mapper.userMapper;
import com.qhs.blog.service.userService;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 2017年5月30日22:41:24实现用户注册逻辑
 */

@Service
public class userServiceImpl implements userService {


    @Autowired
    private userMapper ud;
    @Autowired
    private tokenServiceImpl tokenService;
    @Autowired
    private DefaultHashService defaultHashService;


    //用户注册方法
    public Map<String, Object> userReg(User user) {
        Map<String, Object> result = new HashMap<>();
        //检验用户名、密码、邮箱是否为空
        if (user.getEmail() != null &&
                user.getName() != null &&
                user.getPwd() != null) {
            //如果都不为空，就继续检验数据库中是否存在重复的用户名/邮箱
            if (ud.getByEmail(user) == null && ud.getByName(user) == null) {

                //加私盐
                defaultHashService.setPrivateSalt(new SimpleByteSource("A79B332D1D4D557071CC3539EF75CA50"));
                //将用户给的密码加盐加密
                String hex = defaultHashService.computeHash(new HashRequest.Builder().setSource(ByteSource.Util.bytes(user.getPwd())).build()).toHex();
                //封装回User对象
                user.setPwd(hex);
                //存入数据库
                ud.add(user);
                result.put("result", "success");

                //颁布象征用户身份的Token
                Date date = new Date();
                Map<String, Object> payload = new HashMap<>();
                payload.put("id", ud.getByEmail(user).getId());//将这个user对象的id查出来
                payload.put("iat", date.getTime());//签发时间是服务器当前时间
                payload.put("ext", date.getTime() + 1000 * 60 * 60 * 24 * 7);//过期时间一周
                String token = tokenService.createToken(payload);
                result.put("token", token);
            } else {
                //传过来的user已经存在
                result.put("result", "exist");
            }
        } else {
            //传过来的user不完整
            result.put("result", "incomplete");
        }
        return result;
    }


    //Tk中需要包含等级信息，方便shiro的filter管理。
    @Override
    public Map<String, Object> userAuthc(User user) {
        Map<String, Object> result = new HashMap<>();
//        User getUser = ud.getByName(user);
        //和注册一样，先判断传来的user实体里是不是有空，但是逻辑不一样
        //注册模块邮箱、用户名、密码必须都不为空，但是登陆的话可以用邮箱/用户名登陆，允许其中一个是空的

        User getUser = null;
        //检测完整性
        if (user.getEmail() != null &&
                user.getPwd() != null) {
                //如果邮箱和密码都不为空
                getUser = ud.getByEmail(user);
//                去数据库拿邮箱里的用户对象
        }else if (user.getName() != null &&
                user.getPwd() != null){
                getUser = ud.getByName(user);

        }else{
            result.put("result", "incomplete");
        }

        if(user.getPwd().equals(getUser.getPwd())){
            result.put("result", "success");
            //匹配成功，将用户的id包装为Tk返回
            Date date = new Date();
            Map<String, Object> payload = new HashMap<>();
            payload.put("id", getUser.getId());//将这个user对象的id查出来
            payload.put("iat", date.getTime());//签发时间是服务器当前时间
            payload.put("ext", date.getTime() + 1000 * 60 * 60 * 24 * 7);//过期时间一周
            String token = tokenService.createToken(payload);

            result.put("token", token);
        }else{
            result.put("result", "Username/Password Incorrect");
        }

        return result;
    }


    //TODO 修改用户资料的方法
    public Map<String, Object> userEdit(User user) {
        //拿到用户传进来的User对象

        return null;
    }

    //TODO 提供前端检查是否存在重复用户的方法
    @Override
    public Map<String, Object> userRepeat(User user) {
        return null;
    }




}
