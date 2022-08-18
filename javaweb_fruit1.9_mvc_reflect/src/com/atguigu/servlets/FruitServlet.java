package com.atguigu.servlets;

import com.atguigu.fruit.dao.FruitDao;
import com.atguigu.fruit.dao.FruitDaoImpl;
import com.atguigu.fruit.pojo.Fruit;
import com.atguigu.myssm.myspringmvc.ViewBaseServlet;
import com.atguigu.myssm.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
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
@WebServlet("/fruit.do")
public class FruitServlet extends ViewBaseServlet {
    private FruitDao fruitDao = new FruitDaoImpl();
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置字符编码
        req.setCharacterEncoding("UTF-8");

        String operate = req.getParameter("operate");

        if (StringUtil.isEmpty(operate)){
            operate="index";
        }
        switch (operate){
            case "index":
                index(req,resp);
                break;
            case "add":
                add(req,resp);
                break;
            case "del":
                del(req,resp);
                break;
            case "edit":
                edit(req,resp);
                break;
            case "preAdd":
                preAdd(req,resp);
                break;
            case "update":
                update(req,resp);
                break;
            default:
                throw new RuntimeException("operate值非法!");
        }
    }

    private void index(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String oper = req.getParameter("oper");
        HttpSession session = req.getSession();
        List<Fruit> fruitList = null;
        Long fruitCount = null;
        Integer pageNo = 1;
        String keyword = null;
        if (StringUtil.isNotEmpty(oper)&&"search".equals(oper)){
            //说明是点击表单查询发送过来的请求
            //此时，pageNo应该还原为一
            keyword = req.getParameter("keyword");
            pageNo=1;
            if(StringUtil.isEmpty(keyword)){
                keyword="";
            }
            session.setAttribute("keyword",keyword);
        }
        else {

            //说明不是点击表单查询发送过来的请求，例如上一页下一页，
            //keyword从session作用域获取
            String pageNoStr = req.getParameter("pageNo");
            if (StringUtil.isNotEmpty(pageNoStr)){
                pageNo = Integer.parseInt(pageNoStr);
            }
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
        super.processTemplate("index",req,resp);
//        resp.sendRedirect("fruit.do");
    }
    private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fname = req.getParameter("fname");
        String fcountStr = req.getParameter("fcount");
        int fcount = Integer.parseInt(fcountStr);
        String priceStr = req.getParameter("price");
        int price = Integer.parseInt(priceStr);
        String remark = req.getParameter("remark");
        fruitDao.addFruit(new Fruit(0,fname,price,fcount,remark));
        resp.sendRedirect("fruit.do");
    }
    private void del(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fidStr = req.getParameter("fid");
        if (StringUtil.isNotEmpty(fidStr)){
            int fid = Integer.parseInt(fidStr);
            fruitDao.delFruit(fid);
            resp.sendRedirect("fruit.do");
        }

    }
    private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fidStr = req.getParameter("fid");
        if (StringUtil.isNotEmpty(fidStr)){
            int fid = Integer.parseInt(fidStr);
            Fruit fruit = fruitDao.getFruitByFid(fid);
            req.setAttribute("fruit",fruit);
            super.processTemplate("edit",req,resp);
        }
    }
    private void preAdd(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.processTemplate("add",req,resp);
    }
    private void update(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置字符编码
        req.setCharacterEncoding("UTF-8");
        String fname = req.getParameter("fname");
        String priceStr = req.getParameter("price");
        int price = Integer.parseInt(priceStr);
        String fcountStr = req.getParameter("fcount");
        int fcount = Integer.parseInt(fcountStr);
        String remark = req.getParameter("remark");
        String fidStr = req.getParameter("fid");
        int fid = Integer.parseInt(fidStr);
        //3.执行更新
        fruitDao.updateFruit(new Fruit(fid,fname,price,fcount,remark));

        //4.资源跳转
//        super.processTemplate("index",req,resp);
        //此处需要重定向，需要重新获取fruitList,覆盖到session会话域
        resp.sendRedirect("fruit.do"); }
}
