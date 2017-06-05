package com.qhs.blog.util.shiro.filter;

import com.qhs.blog.serviceImpl.tokenServiceImpl;
import net.minidev.json.JSONObject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * Created by QHS on 2017/5/31.
 */
public class adminFilter extends AccessControlFilter {
    @Autowired
    private tokenServiceImpl tokenService;
    //由于loginFilter已经检验过Tk，这里不检查Tk的有效性等，只对TK中的uid和参数的uid做检测

    JSONObject resp = new JSONObject();

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        boolean flag = false;

        HttpServletRequest req = WebUtils.toHttp(servletRequest);
        String reqToken = req.getParameter("token");

        Map<String, Object> resultMap = tokenService.validToken(reqToken);

        if (resultMap.get("uid") == servletRequest.getParameter("uid")) {
            flag = true;
        } else {
            resp.put("warning", "身份不正确");
        }
        return flag;

    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json; charset=utf-8");
        httpResponse.getWriter().write(resp.toJSONString());

        return false;
    }
}
