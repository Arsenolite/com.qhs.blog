package com.qhs.blog.util.shiro.filter;


import com.qhs.blog.dao.redisDao;
import com.qhs.blog.mapper.userMapper;
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
public class loginFilter extends AccessControlFilter {

    @Autowired
    private tokenServiceImpl tokenService;

    @Autowired
    private userMapper ud;


    JSONObject resp = new JSONObject();

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        boolean flag = false;
        /*
        这个Filter是用来保护那些登陆才能访问的资源，用户得带Tk访问这些资源，而Filter就用来获取Tk，Realm解析TK。
        userController只负责颁发Tk而不负责检查。
        看到的文章里，这样的Filter本来是用来每次请求都带上用户名密码的，既然引入jwt就拜托它了。
        */
        // 从参数里拿到Tk
        HttpServletRequest req = WebUtils.toHttp(servletRequest);
        String reqToken = req.getParameter("token");

        Map<String, Object> resultMap = tokenService.validToken(reqToken);
        switch ((String) resultMap.get("state")) {
            case "EXPIRED":
                resp.put("warning", "登陆已过期");
                break;
            case "VALID":
                //降低开销，不从数据库验证
                //TODO 判断是否是请求的资源作者
                flag = true;
                break;
            case "INVALID":
                resp.put("warning", "请先登录");
                break;
        }
        return flag;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;
        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//        httpResponse.getWriter().write("login error");
//        要采取JSON格式返回
        httpResponse.setCharacterEncoding("UTF-8");
        httpResponse.setContentType("application/json; charset=utf-8");
        httpResponse.getWriter().write(resp.toJSONString());
        return false;

    }


}
