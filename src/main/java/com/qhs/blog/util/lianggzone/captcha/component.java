package com.qhs.blog.util.lianggzone.captcha;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.Buffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.qhs.blog.dao.redisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <h3>概要:</h3><p>CaptchaComponent</p>
 * <h3>功能:</h3><p>图片认证码工具类</p>
 * <h3>履历:</h3>
 * <li>2017年5月16日  v0.1 版本内容: 新建</li>
 * @author 粱桂钊
 * @since 0.1
 */
@Component
public class component {

    //Component只负责返回一个验证码和图片的Map，不负责Token鉴权也不负责数据库读写，也不直接操作Servlet流，高内聚低耦合
    //所以无需任何参数，直接返回一个Map，存有一张图和一个字符串
    //    @Autowired
//    private redisDao redisBaseDao;

    // 图片的宽度
    private static final int CAPTCHA_WIDTH = 90;
    // 图片的高度
    private static final int CAPTCHA_HEIGHT = 20;
    // 验证码的个数
    private static final int CAPTCHA_CODECOUNT = 4;

    private static final int XX = 15;
    private static final int CAPTCHA_FONT_HEIGHT = 18;
    private static final int CAPTCHA_CODE_Y = 16;
    private static final char[] codeSequence = {
            'A', 'B', 'C', 'D', 'E', 'F', 'G',
            'H', 'I', 'J', 'K', 'L', 'M', 'N',
            'O', 'P', 'Q', 'R', 'S', 'T',
            'U', 'V', 'W', 'X', 'Y', 'Z',
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

    // 过期时间为60秒
    private static final long EXPIRE_MINUTES = 60;

    public Map<String,Object> genCaptcha(){

        Map<String,Object> captcha = new HashMap<String,Object>();
        // 定义图像 Buffer
        BufferedImage buffImg = new BufferedImage(CAPTCHA_WIDTH, CAPTCHA_HEIGHT, BufferedImage.TYPE_INT_RGB);
        // 创建一个绘制图像的对象
        Graphics2D g = buffImg.createGraphics();
        // 创建一个随机数生成器类
        Random random = new Random();
        // 将图像填充为白色
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, CAPTCHA_WIDTH, CAPTCHA_HEIGHT);
        // 设置字体
        g.setFont(new Font("Fixedsys", Font.BOLD, CAPTCHA_FONT_HEIGHT));
        // 设置字体边缘光滑
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // 画边框
        g.setColor(Color.BLACK);
        g.drawRect(0, 0, CAPTCHA_WIDTH - 1, CAPTCHA_HEIGHT - 1);
        // 随机产生40条干扰线，使图象中的认证码不易被其它程序探测到。
        g.setColor(Color.BLACK);
        for (int i = 0; i < 40; i++) {
            int x = random.nextInt(CAPTCHA_WIDTH);
            int y = random.nextInt(CAPTCHA_HEIGHT);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }

        // 保存随机产生的验证码，以便用户登录后进行验证
        StringBuffer randomCode = new StringBuffer();
        int red = 0, green = 0, blue = 0;
        // 随机产生验证码
        for (int i = 0; i < CAPTCHA_CODECOUNT; i++) {
            // 得到随机产生的验证码数字
            String code = String.valueOf(codeSequence[random.nextInt(36)]);
            // 产生随机的颜色分量来构造颜色值，这样输出的每位数字的颜色值都将不同
            red = random.nextInt(255);
            green = random.nextInt(255);
            blue = random.nextInt(255);
            // 用随机产生的颜色将验证码绘制到图像中
            g.setColor(new Color(red, green, blue));
            g.drawString(code, (i + 1) * XX, CAPTCHA_CODE_Y);
            // 将产生的随机数组合在一起
            randomCode.append(code);
        }
        g.dispose();
        //解耦，将生成的验证码和图片存入Map供Service使用
        //这个轮子把验证码字符串直接和生成图片的逻辑放一起了……懒得取出来复用在mail里了……
        captcha.put("code",randomCode.toString());
        captcha.put("img",buffImg);
        return captcha;
//操作response的语句移到Controller
//        this.redisBaseDao.addValue(token, randomCode.toString());
//        this.redisBaseDao.expire(token, EXPIRE_MINUTES, TimeUnit.MINUTES);
    }
}