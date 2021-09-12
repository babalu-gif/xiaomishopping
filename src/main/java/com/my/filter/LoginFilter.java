package com.my.filter;

import com.my.pojo.Admin;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class LoginFilter implements Filter
{
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("进入拦截器");
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException
    {
        System.out.println("过滤拦截");
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String path = request.getContextPath();
        System.out.println(path);
        /* || "/myWeb".equals(path)*/
        if("/myWeb/admin/login.jsp".equals(path) || "/myWeb/admin/login.do".equals(path))
        {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        HttpSession session = request.getSession(false);
        Admin admin = (Admin) session.getAttribute("admin");
        if(admin != null)
        {
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else
        {
            System.out.println(request.getContextPath());
            // 重定向，转发会停留在当前路径
            // 获取当前项目名称
            request.setAttribute("errmsg", "你还未登录，请先登录");
            request.getRequestDispatcher(request.getContextPath() + "/admin/login.jsp").forward(request, response);
        }
    }

    @Override
    public void destroy() {

    }
}
