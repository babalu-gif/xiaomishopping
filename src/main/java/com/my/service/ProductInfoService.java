package com.my.service;

import com.github.pagehelper.PageInfo;
import com.my.pojo.ProductInfo;
import com.my.pojo.vo.ProductVo;

import java.util.List;

public interface ProductInfoService
{
    // 查询所有商品
    List<ProductInfo> getAll();

    // 分页功能实现
    PageInfo splitPage(int pageNum, int pageSize);

    // 添加商品功能
    int save(ProductInfo productInfo);

    // 根据主键查询商品
    ProductInfo getById(int id);

    // 更新商品
    int update(ProductInfo productInfo);

    // 删除商品
    int delete(int pid);

    // 删除多个商品
    int deleteBatch(String[] pids);

    // 多条件查询分页
    PageInfo splitPageVo(ProductVo vo, int pageSize);
}
