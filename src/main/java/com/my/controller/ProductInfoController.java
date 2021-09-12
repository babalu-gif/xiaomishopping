package com.my.controller;

import com.github.pagehelper.PageInfo;
import com.my.pojo.ProductInfo;
import com.my.pojo.vo.ProductVo;
import com.my.service.ProductInfoService;
import com.my.utils.FileNameUtil;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping(value = "/product")
public class ProductInfoController
{
    private static final int PAGE_SIZE = 5;
    private String saveFileName = "";
    @Autowired
    private ProductInfoService productInfoService;

    // 显示全部信息（不分页）
    @RequestMapping(value = "/getAll.do")
    public String getAll(HttpServletRequest request)
    {
        List<ProductInfo> list = productInfoService.getAll();
        request.setAttribute("list", list);
        return "product";
    }

    // 显示第一页的5条记录（分页）
    @RequestMapping(value = "/split.do")
    public String split(HttpServletRequest request)
    {
        PageInfo info = null;
        Object vo = request.getSession().getAttribute("productVo");
        if(vo != null)
        {
            info = productInfoService.splitPageVo((ProductVo) vo, PAGE_SIZE);
            request.getSession().setAttribute("vo", vo);
            /*request.getSession().removeAttribute("productVo");*/
        }
        else
        {
            info = productInfoService.splitPage(1, PAGE_SIZE);
        }

        request.setAttribute("pb", info);
        return "product";
    }

    // ajax分页反页处理
    @ResponseBody
    @RequestMapping(value = "/ajaxSplit.do")
    public void ajaxSplit(HttpSession session, int page, ProductVo vo)
    {
        // 如果为第0页，当成第一页来处理
        page = (page==0) ? 1 : page;
        // 取得当前page参数页面的数据
        PageInfo info = productInfoService.splitPageVo(vo, PAGE_SIZE);
        session.setAttribute("pb", info);
    }

    // 异步ajax文件上传处理
    @ResponseBody
    @RequestMapping(value = "/ajaxImg.do")
    public Object ajaxImg(HttpServletRequest request, MultipartFile pimage)
    {
        // 提取生成文件名(UUID+文件名后缀)
        saveFileName = FileNameUtil.getUUIDFileName()+FileNameUtil.getFileType(pimage.getOriginalFilename());
        // 得到项目中图片存储的路径
        String path = request.getServletContext().getRealPath("/image_big");
        System.out.println("path:"+path);
        // 转存
        try
        {
            pimage.transferTo(new File(path + File.separator + saveFileName));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        // 返回给客户端JSON对象，封装图片的路径，为了在页面实现立即回显
        JSONObject object = new JSONObject();
        object.put("imgurl", saveFileName);

        return object.toString();
    }

    @RequestMapping(value = "/save.do")
    public String save(HttpServletRequest request, ProductInfo productInfo)
    {
        productInfo.setpImage(saveFileName);
        productInfo.setpDate(new Date());
        int num = -1;
        try
        {
            num = productInfoService.save(productInfo);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        if(num > 0)
        {
            // 新增商品成功后，销毁当前查询文本框的数据
            request.getSession().removeAttribute("vo");
            request.setAttribute("msg", "增加成功！");
        }
        else
        {
            request.setAttribute("msg", "增加失败！");
        }

        // 清空saveFileName的内容
        saveFileName = "";
        return "forward:/product/split.do";
    }

    @RequestMapping(value = "/one.do")
    public String one(HttpSession session, int pid, ProductVo vo, Model model)
    {
        ProductInfo productInfo = productInfoService.getById(pid);
        model.addAttribute("product", productInfo);
        // 将多条件及页码放入session中，更新处理结果后分页时读取条件个页码进行处理
        session.setAttribute("productVo", vo);
        return "update";
    }

    @RequestMapping(value = "/update.do")
    public String update(HttpServletRequest request, ProductInfo productInfo)
    {
        // 如果有上传过，则使用当前文件名称，否则使用隐藏表单域的pImage图片名称
        if(!"".equals(saveFileName))
        {
            productInfo.setpImage(saveFileName);
        }
        int num = -1;
        try
        {
            num = productInfoService.update(productInfo);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        if(num > 0)
        {
            request.setAttribute("msg", "更新成功！");
        }
        else
        {
            request.setAttribute("msg", "更新失敗！");
        }
        saveFileName = "";
        return "forward:/product/split.do";
    }

    // 此时虽然是ajax请求，但是还要使用ajax进行跳转，不能使用 @ResponseBody
    @RequestMapping(value = "/delete.do")
    public String delete(HttpServletRequest request, HttpSession session, ProductVo vo, int pid)
    {
        int num = -1;
        try
        {
            num = productInfoService.delete(pid);
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
        if(num > 0)
        {
            request.setAttribute("msg", "删除成功！");
            session.setAttribute("deleteProductVo", vo);
        }
        else
        {
            request.setAttribute("msg", "删除失败");
        }
        return "forward:/product/deleteAjaxSplit.do";
    }

    @ResponseBody
    @RequestMapping(value = "/deleteAjaxSplit.do", produces = "text/html;charset=UTF-8")
    public Object deleteAjaxSplit(HttpServletRequest request, int page)
    {
        PageInfo info = null;
        Object vo = request.getSession().getAttribute("deleteProductVo");
        if(vo != null)
        {
            info = productInfoService.splitPageVo((ProductVo) vo, PAGE_SIZE);
        }
        else
        {
            info = productInfoService.splitPage(1, PAGE_SIZE);
        }
        request.getSession().setAttribute("pb", info);
        return request.getAttribute("msg");
    }

    // 此时虽然是ajax请求，但是还要使用ajax进行跳转，不能使用 @ResponseBody
    @RequestMapping(value = "/deleteBatch.do")
    public String deleteBatch(HttpServletRequest request, String[] pids)
    {
        try
        {
            int num = productInfoService.deleteBatch(pids);
            if(num > 0)
            {
                request.setAttribute("msg", "批量删除成功！");
            }
            else
            {
                request.setAttribute("msg", "批量删除失败！");
            }
        }
        catch (Exception exception)
        {
            request.setAttribute("msg", "商品不可删除");
        }
        return "forward:/product/deleteAjaxSplit.do";
    }

}
