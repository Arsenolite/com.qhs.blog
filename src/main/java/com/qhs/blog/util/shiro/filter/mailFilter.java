package com.qhs.blog.util.shiro.filter;

import com.qhs.blog.serviceImpl.tokenServiceImpl;
import net.minidev.json.JSONArray;
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
public class mailFilter extends AccessControlFilter {
    @Autowired
    private tokenServiceImpl tokenService;
    JSONObject resp = new JSONObject();
    //写过captchaFilter了，这个大同小异，只需要验证value是不是mailAuthed就行了。
    //通过邮件验证之后会重新颁布Tk。
    //可惜如果token expired没法输出……
    //其实是有的，要在类里组出JSON对象

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        boolean flag = false;
        HttpServletRequest req = WebUtils.toHttp(servletRequest);
        String token = req.getParameter("token");
        Map<String, Object> resultMap = tokenService.validToken(token);
        switch ((String) resultMap.get("state")) {
            case "EXPIRED":
                resp.put("warning","邮箱验证码已过期");
                break;
            case "VALID":
                JSONObject jo = (JSONObject) resultMap.get("data");
                String value = (String) jo.get("value");
                if (value.equals("mailAuthed")) {
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

        resp.put("msg", "请先通过邮箱认证");
        servletResponse.setCharacterEncoding("UTF-8");
        servletResponse.setContentType("application/json; charset=utf-8");
        servletResponse.getWriter().write(resp.toJSONString());
        return false;
    }
}
