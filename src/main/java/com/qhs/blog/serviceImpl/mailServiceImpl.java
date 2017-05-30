package com.qhs.blog.serviceImpl;

import com.qhs.blog.bean.User;
import com.qhs.blog.dao.redisDao;
import com.qhs.blog.service.mailService;

import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;


import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * Created by QHS on 2017/5/28.
 */
@Component
public class mailServiceImpl implements mailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;
    @Autowired
    private redisDao redisDao;


    //生成邮件并发送
    public void genMailByUser(User user, String token) {

        String target = user.getEmail();
        String username = user.getName();
        String mailCode = genMailCode();
        if (null == username) {
            username = "尊敬的用户";
        }
        String text = null;
        Map<String, String> map = new HashMap<>(1);
        map.put("username", username);
        map.put("mailCode", mailCode);
        try {
            Template template = freeMarkerConfigurer.getConfiguration().getTemplate("mailCode.ftl");
            text = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (TemplateException e) {
            e.printStackTrace();
        }
        sendMail(target, text);
        //存入redis数据库
        redisDao.addValue(token, mailCode);
        //指定这份映射存在一小时
        redisDao.expire(token, 1, TimeUnit.HOURS);
    }

    //检测是否合法
    public String findMailCode(String token) {
        return redisDao.getValue(token);
    }

    public void sendMail(String target, String content) {

        try {
            //创建多媒体邮件
            MimeMessage mmm = javaMailSender.createMimeMessage();
            //修改邮件体
            MimeMessageHelper mmh = new MimeMessageHelper(mmm, true, "UTF-8");
            //设置发件人信息
            mmh.setFrom("1335734657@qq.com");
            //收件人
            mmh.setTo(target);
            //主题
            mmh.setSubject("邮箱验证码");
            //内容
            mmh.setText(content, true);
            //送出
            javaMailSender.send(mmm);
        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }


    //生成随机验证码
    public String genMailCode() {
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            //之前大小写都有，总共62个字符，现在是36个了，而我忘记改，导致越界。2017年5月30日21:36:29
            int num = random.nextInt(36);
            buf.append(str.charAt(num));
        }
        return buf.toString();
    }

}
