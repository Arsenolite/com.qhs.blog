package com.qhs.blog.util.shiro.filter;


import com.qhs.blog.serviceImpl.tokenServiceImpl;
import net.minidev.json.JSONObject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Created by QHS on 2017/5/31.
 */
public class captchaFilter extends AccessControlFilter {
    @Autowired
    private tokenServiceImpl tokenService;

    JSONObject resp = new JSONObject();

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        boolean flag = false;

//        JSONObject resp = new JSONObject();
//        这个resp用不到，这个方法压根不会写servlet流
        //从HTTP Headers中取出Token（不放在JSON中避免影响自动组装）(已修改)
        //统一Token放在参数内
        HttpServletRequest req = WebUtils.toHttp(servletRequest);
        String token = req.getParameter("token");
//        String token = req.getHeader("Token");
        //调用tokenService检验Token的有效性
        Map<String, Object> resultMap = tokenService.validToken(token);
        switch ((String) resultMap.get("state")) {
            case "EXPIRED":
                resp.put("warning","授权已过期");
                break;
            case "VALID":
                //在此验证Token中的信息
                //如果信息正确，返回true
                JSONObject jo = (JSONObject) resultMap.get("data");
                String value = (String) jo.get("value");
                //如果能拿到和邮件验证码用的Tk，肯定是通过了图片验证码校验。
                //保持同时只存在一个Tk，避免混淆。
                if (value.equals("captchaAuthed")&&value.equals("getMailCode")) {
//                    我怎么会想到在通过验证之后往servlet流里写东西的……
//                    我他妈就是个傻逼
//                    servletResponse.setCharacterEncoding("UTF-8");
//                    servletResponse.setContentType("application/json; charset=utf-8");
//                    servletResponse.getWriter().write(resp.toJSONString());
                    //这个flag是true之后Filter就放行。
                    //感慨两句，果然还是知识面不够广，用类的形式写Filter之前没做过。。
                    flag = true;
                }
                break;
            case "INVALID":

                break;
        }

        return flag;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        //返回一个json字符串

        resp.put("msg", "请输入验证码");
        servletResponse.setCharacterEncoding("UTF-8");
        servletResponse.setContentType("application/json; charset=utf-8");
        servletResponse.getWriter().write(resp.toJSONString());
        return false;
    }

}
