package cn.zm.blog.web;



import cn.zm.blog.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zm on 2017-4-18.
 */
@WebFilter(filterName = "AccessFilter")
public class AccessFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        //类型转换
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;
        //1 获取登录用户信息
        //2 如果没有登录用户信息，重定向到log_in.html
        String path = req.getRequestURI();
        System.out.println(path);
        if (path.endsWith("login.html") || path.contains("/alert/")) {
            //设置HTTP协议头，避免浏览器缓存html页面
            res.addHeader("Cache-Control", "no-cache");
            chain.doFilter(request, response);
            return;
        }

        User user = (User) req.getSession().getAttribute("loginUser");
        if (user == null) {
            //如果user为null则表示没有登录
            //重定向到 login.html
            //采用绝对路径重定向！可以避免错误
            String login = req.getContextPath() + "/login.html";
            // /note/login.html
            res.sendRedirect(login);
            return;
        }
        //设置HTTP协议头，避免浏览器缓存html页面
        res.addHeader("Cache-Control", "no-cache");
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
