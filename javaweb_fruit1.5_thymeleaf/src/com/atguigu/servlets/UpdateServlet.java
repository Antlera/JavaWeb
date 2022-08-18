package com.atguigu.servlets;

import com.atguigu.fruit.dao.FruitDao;
import com.atguigu.fruit.dao.FruitDaoImpl;
import com.atguigu.fruit.pojo.Fruit;
import com.atguigu.myssm.myspringmvc.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author by Antlers Email:3219599757@qq.com
 * @Description
 * @date 2022/7/23.
 * @package_name com.atguigu.servlets
 **/
@WebServlet("/update.do")
public class UpdateServlet extends ViewBaseServlet {
    private FruitDao fruitDao = new FruitDaoImpl();
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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
        resp.sendRedirect("index");
    }
}
