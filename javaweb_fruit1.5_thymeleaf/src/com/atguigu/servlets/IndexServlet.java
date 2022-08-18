package com.atguigu.servlets;

import com.atguigu.fruit.dao.FruitDao;
import com.atguigu.fruit.dao.FruitDaoImpl;
import com.atguigu.fruit.pojo.Fruit;
import com.atguigu.myssm.myspringmvc.ViewBaseServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FruitDaoImpl fruitDao = new FruitDaoImpl();
        List<Fruit> fruitList = fruitDao.getFruitList();
        //保存到session作用域
        HttpSession session = req.getSession();
        session.setAttribute("fruitList",fruitList);
        //thymeleaf将这个逻辑视图名称对应到物理视图名称上
        //逻辑视图名称：index
        //物理视图名称：view-prefix + 逻辑视图名称 + view-suffix
        //真实视图为    /             index       .html
        super.processTemplate("index",req,resp);
    }
}
