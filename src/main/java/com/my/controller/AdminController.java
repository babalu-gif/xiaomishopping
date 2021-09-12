package com.my.controller;


import com.my.pojo.Admin;
import com.my.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/admin")
public class AdminController {
    //创建业务逻辑层的对象
    @Autowired
    private AdminService adminService;

    @RequestMapping("/login.do")
    public String login(HttpServletRequest request, String name, String pwd, Model model)
    {
       Admin admin = adminService.login(name, pwd);
        if (admin != null)
        {
            request.setAttribute("admin", admin);
            model.addAttribute("name", admin.getaName());
            return "main";
        }
        else
        {
            model.addAttribute("errmsg", "用户名或密码不正确！");
            return "login";
        }
    }

}