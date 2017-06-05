package com.qhs.blog.serviceImpl;

import com.qhs.blog.bean.User;
import com.qhs.blog.mapper.userMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Map;


/**
 * Created by QHS on 2017/5/29.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-*.xml"})
public class userServiceImplTest {
//    @Autowired
//    private userMapper ud;
    @Autowired
    private userServiceImpl usi;
    @Test
    public void testMapper() throws InterruptedException {
//        String cfg = "spring/spring-dao.xml";
//        ApplicationContext ac = new ClassPathXmlApplicationContext(cfg);
//        userMapper ud = (userMapper) ac.getBean("userMapper");
        //        User user = ud.getUser(1);
//        System.out.println(user.getEmail());

//        String mmm = usi.testGet();
//        System.out.println(mmm);

            User user = new User();
        Map<String,Object> map = null;
//            测试修改用户信息
//            user.setId(1);
//            user.setPwd("567890");
//            Map<String,Object> map = usi.userEdit(user);
            //测试添加用户
//        user.setName("qhs");
//        user.setPwd("cnmb");
//        user.setEmail("quhansheng@gmail.com");
//        map = usi.userReg(user);
            //测试Ajax查重
//            user.setName("arsenolit");
//            Map<String, Object> result = usi.userRepeat(user);
//            System.out.print(result.get("result"));
            //测试查看用户信息
//        Map<String, Object> result = usi.userInfo(1);

        Thread.sleep(123456788);

    }

}
