package com.my.service;

import com.my.pojo.Admin;

public interface AdminService
{
    //登录
    public Admin login(String name, String pwd);
}
