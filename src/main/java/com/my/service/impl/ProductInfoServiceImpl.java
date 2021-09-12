package com.my.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.my.mapper.ProductInfoMapper;
import com.my.pojo.ProductInfo;
import com.my.pojo.ProductInfoExample;
import com.my.pojo.vo.ProductVo;
import com.my.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductInfoServiceImpl implements ProductInfoService
{
    @Autowired
    private ProductInfoMapper productInfoMapper;

    @Override
    public List<ProductInfo> getAll()
    {
        return productInfoMapper.selectByExample(new ProductInfoExample());
    }

    @Override
    public PageInfo splitPage(int pageNum, int pageSize)
    {
        // 分页插件使用PageHelper工具类完成分页设置
        PageHelper.startPage(pageNum, pageSize);

        // 进行PageInfo的数据封装
        // 进行有条件的查询操作，必须要创建ProductInfoExample对象
        ProductInfoExample example = new ProductInfoExample();

        // 排序（按主键降序排序）
        example.setOrderByClause("p_id desc");

        // 排序完，取集合，一定在取集合之前，设置PageHelper.startPage(pageNum, pageSize);
        List<ProductInfo> list = productInfoMapper.selectByExample(example);

        // 将查到的集合封装到PageInfo对象中
        PageInfo<ProductInfo> pageInfo = new PageInfo<>(list);
        return pageInfo;
    }

    @Override
    public int save(ProductInfo productInfo)
    {
        return productInfoMapper.insert(productInfo);
    }

    @Override
    public ProductInfo getById(int id)
    {
        return productInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int update(ProductInfo productInfo)
    {

        return productInfoMapper.updateByPrimaryKeySelective(productInfo);
    }

    @Override
    public int delete(int pid)
    {
        return productInfoMapper.deleteByPrimaryKey(pid);
    }

    @Override
    public int deleteBatch(String[] pids)
    {
        return productInfoMapper.deleteBatch(pids);
    }

    @Override
    public PageInfo<ProductInfo> splitPageVo(ProductVo vo, int pageSize)
    {
        PageHelper.startPage(vo.getPage(), pageSize);
        List<ProductInfo> list = productInfoMapper.selectConditionSplitPage(vo);
        return new PageInfo<>(list);
    }
}
