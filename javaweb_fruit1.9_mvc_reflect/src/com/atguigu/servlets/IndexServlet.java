package com.atguigu.servlets;

import com.atguigu.fruit.dao.FruitDaoImpl;
import com.atguigu.fruit.pojo.Fruit;
import com.atguigu.myssm.myspringmvc.ViewBaseServlet;
import com.atguigu.myssm.util.StringUtil;
import javassist.compiler.ast.Keyword;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @author by Antlers Email:3219599757@qq.com
 * @Description
 * @date 2022/7/22.
 * @package_name com.atguigu.servlets
 **/
@WebServlet("/index")
public class IndexServlet extends ViewBaseServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doGet(req,resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
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
    }

}
