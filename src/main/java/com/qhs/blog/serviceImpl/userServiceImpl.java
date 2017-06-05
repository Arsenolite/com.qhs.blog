package com.qhs.blog.serviceImpl;

import com.qhs.blog.bean.User;
import com.qhs.blog.dao.redisDao;
import com.qhs.blog.mapper.userMapper;
import com.qhs.blog.service.userService;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.SimpleByteSource;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

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
    @Autowired
    private redisDao redisDao;
    long ext = (long)86400000 * 30;//过期时间一个月

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
                //将用户最后登录时间改成现在
                user.setLast_login_at(new Timestamp(System.currentTimeMillis()));
                //存入数据库
                ud.add(user);
                result.put("result", "success");

                //颁布象征用户身份的Token
                Date date = new Date();
                Map<String, Object> payload = new HashMap<>();
                payload.put("id", ud.getByEmail(user).getId());//将这个user对象的id查出来
                payload.put("level", ud.getByEmail(user).getLevel());//将这个user对象的id查出来
                payload.put("iat", date.getTime());//签发时间是服务器当前时间
                payload.put("ext", date.getTime() + ext);//过期时间半个月
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


    //用户认证
    @Override
    public Map<String, Object> userAuthc(User user) {
        Map<String, Object> result = new HashMap<>();
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
            //将用户最后登录时间改成现在
            user.setLast_login_at(new Timestamp(System.currentTimeMillis()));
            ud.update(user);

            result.put("result", "success");

            //匹配成功，将用户的id包装为Tk返回
            Date date = new Date();
            Map<String, Object> payload = new HashMap<>();
            payload.put("id", getUser.getId());//将这个user对象的id查出来
            payload.put("level", getUser.getLevel());//将这个user对象的等级查出来
            payload.put("iat", date.getTime());//签发时间是服务器当前时间
            payload.put("ext", date.getTime() + ext);
            String token = tokenService.createToken(payload);

            result.put("token", token);
        }else{
            result.put("result", "Username/Password Incorrect");
        }

        return result;
    }
    //作废Tk的方法（将Tk存入redis，到Tk本身的过期时间就过期）
    @Override
    public Map<String, Object> userLogout(String token) {
        Map<String, Object> result = new HashMap<>();
        //感觉逻辑思维有点跟不上了……这边应该还是要检测传进来的Tk是否有效，昨晚给忘了
        Map<String, Object> payload = tokenService.validToken(token);
        //将Token的过期时间拿出来
        long expireTime = (long)payload.get("ext");
        //存入黑名单，加个过期时间
        redisDao.addValue(token,"expired");
        //过期时间是Tk的过期时间减去服务器当前时间
        redisDao.expire(token,expireTime-new Date().getTime(), TimeUnit.MILLISECONDS);
        return result;
    }

    //修改用户资料的方法
    public Map<String, Object> userEdit(User user) {
        Map<String, Object> result = new HashMap<>();
        //拿到用户传进来的User对象
        int flag = ud.update(user);
        //不知道那个FLAG有没有用……写个测试类
        //事实证明是有用的，
        result.put("result", flag);
        return result;
    }

    //前端Ajax查重的办法
    @Override
    public Map<String, Object> userRepeat(User user) {
        //参照用户注册方法
        Map<String, Object> result = new HashMap<>();
        //由于是提供前端Ajax的方法，不设过滤器
        User getUser = null;
        //看前端给的是用户名还是密码
        if (user.getEmail() != null) {
            getUser = ud.getByEmail(user);
            //去数据库拿邮箱里的用户对象
        }else if (user.getName() != null){
            getUser = ud.getByName(user);
        }
        if(getUser != null){
            result.put("result", "exist");
        }else{
            result.put("result", "inexistence");
        }

        return result;
    }


    //根据id获取用户信息的方法
    @Override
    public Map<String, Object> userInfo(Integer uid) {
        User user = ud.getUser(uid);
        JSONObject jo = new JSONObject(user);//这里采用org.json的JO，转换对象方便
        return jo.toMap();
    }


}
