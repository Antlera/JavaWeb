package com.atguigu.fruit.controllers;

import com.atguigu.fruit.dao.FruitDao;
import com.atguigu.fruit.dao.FruitDaoImpl;
import com.atguigu.fruit.pojo.Fruit;
import com.atguigu.myssm.myspringmvc.ViewBaseServlet;
import com.atguigu.myssm.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author by Antlers Email:3219599757@qq.com
 * @Description
 * @date 2022/7/24.
 * @package_name com.atguigu.servlets
 **/
public class FruitController{
    private FruitDao fruitDao = new FruitDaoImpl();
    private String index(String oper, String keyword, Integer pageNo, HttpServletRequest req){
        HttpSession session = req.getSession();
        List<Fruit> fruitList = null;
        Long fruitCount = null;
        if (pageNo==null){
            pageNo=1;
        }
        if (StringUtil.isNotEmpty(oper)&&"search".equals(oper)){
            //说明是点击表单查询发送过来的请求
            //此时，pageNo应该还原为一
            pageNo=1;
            if(StringUtil.isEmpty(keyword)){
                keyword="";
            }
            session.setAttribute("keyword",keyword);
        }
        else {

            //说明不是点击表单查询发送过来的请求，例如上一页下一页，
            //keyword从session作用域获取
            Object keywordObj = session.getAttribute("keyword");
            if (keywordObj!=null){
                keyword = (String)keywordObj;
            }else {
                keyword = "";
            }
        }
        FruitDaoImpl fruitDao = new FruitDaoImpl();
        fruitList = fruitDao.getFruitList(pageNo,keyword);
        fruitCount = fruitDao.getFruitCount(keyword);
        Long pageCount = (fruitCount+5-1)/5;
        //thymeleaf将这个逻辑视图名称对应到物理视图名称上
        //逻辑视图名称：index
        //物理视图名称：view-prefix + 逻辑视图名称 + view-suffix
        //真实视图为    /             index       .html
        //保存到session作用域
        session.setAttribute("fruitList",fruitList);
        //重新更新当前页的值
        session.setAttribute("pageNo",pageNo);
        session.setAttribute("pageCount",pageCount);
        return "index";
    }
    private String add(String fname,Integer price,Integer fcount,String remark){
        fruitDao.addFruit(new Fruit(0,fname,price,fcount,remark));
        return "redirect:fruit.do";
    }
    private String del(Integer fid) throws ServletException{
        if (fid!=null){
            fruitDao.delFruit(fid);
            return "redirect:fruit.do";
        }
        return "error";
    }
    private String edit(HttpServletRequest req,Integer fid){
        if (fid!=null){
            Fruit fruit = fruitDao.getFruitByFid(fid);
            req.setAttribute("fruit",fruit);
            return "edit";
        }
        return "error";
    }
    private String preAdd(HttpServletRequest req) {
        return "add";
    }
    private String update(Integer fid,String fname,Integer price,Integer fcount,String remark){
        //3.执行更新
        fruitDao.updateFruit(new Fruit(fid, fname, price, fcount, remark));
        //4.返回资源跳转消息
        return "redirect:fruit.do";
    }
}
