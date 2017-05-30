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
    public void testMapper(){
//        String cfg = "spring/spring-dao.xml";
//        ApplicationContext ac = new ClassPathXmlApplicationContext(cfg);
//        userMapper ud = (userMapper) ac.getBean("userMapper");


//        User user = ud.getUser(1);
//        System.out.println(user.getEmail());

//        String mmm = usi.testGet();
//        System.out.println(mmm);


    }

}
