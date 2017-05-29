package com.qhs.blog.serviceImpl;

import com.qhs.blog.bean.User;
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

/**
 * Created by QHS on 2017/5/28.
 */
@Component
public class mailServiceImpl implements mailService {
    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private FreeMarkerConfigurer freeMarkerConfigurer;


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

    public String genMailByUser(User user) {
        String target = user.getEmail();
        String username = user.getName();
        String mailCode = genMailCode();
        String text = null;
        Map<String, String> map = new HashMap<String, String>(1);
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
        return mailCode;
    }

    public String genMailCode() {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuffer buf = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            int num = random.nextInt(62);
            buf.append(str.charAt(num));
        }
        return buf.toString();
    }

}
