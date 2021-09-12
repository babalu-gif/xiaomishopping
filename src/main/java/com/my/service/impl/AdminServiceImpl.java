package com.my.service.impl;


import com.my.mapper.AdminMapper;
import com.my.pojo.Admin;
import com.my.pojo.AdminExample;
import com.my.service.AdminService;
import com.my.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService
{
    //spring框架为你提供mapper实现类的对象
    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin login(String name, String pwd)
    {
        //根据用户名查用户对象回来，取出查回来的对象的密码和传来的密码对比，判断登录是否成功
        //创建条件封装的对象AdminExample
        AdminExample example = new AdminExample();
        //将用户名作为条件封装进AdminExample对象
        example.createCriteria().andANameEqualTo(name);

        List<Admin> list = adminMapper.selectByExample(example);
        if(list != null && list.size() > 0)
        {
            Admin admin = list.get(0);
            if(MD5Util.getMD5(pwd).equals(admin.getaPass()))
            {
                return admin;
            }
        }
        return null;
    }
}