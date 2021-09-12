package com.my.service.impl;

import com.my.mapper.ProductTypeMapper;
import com.my.pojo.ProductType;
import com.my.pojo.ProductTypeExample;
import com.my.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("ProductTypeServiceImpl")
public class ProductTypeServiceImpl implements ProductTypeService
{
    @Autowired
    private ProductTypeMapper productTypeMapper;


    @Override
    public List<ProductType> getAll()
    {
        return productTypeMapper.selectByExample(new ProductTypeExample());
    }
}
