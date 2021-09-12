package com.my;

import com.my.mapper.ProductInfoMapper;
import com.my.pojo.ProductInfo;
import com.my.pojo.ProductInfoExample;
import com.my.pojo.vo.ProductVo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext-dao.xml", "classpath:applicationContext-service.xml"})
public class MyTest
{
    @Autowired
    ProductInfoMapper productInfoMapper;

    @Test
    public void test()
    {
        ProductVo vo = new ProductVo();
        vo.setTypeid(1);
        List<ProductInfo> productInfoList = productInfoMapper.selectConditionSplitPage(vo);
        for(ProductInfo p : productInfoList)
        {
            System.out.println(p.getpName());
        }
    }
}
