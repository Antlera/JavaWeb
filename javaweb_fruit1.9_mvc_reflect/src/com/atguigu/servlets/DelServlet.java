package com.atguigu.servlets;

import com.atguigu.fruit.dao.FruitDao;
import com.atguigu.fruit.dao.FruitDaoImpl;
import com.atguigu.myssm.myspringmvc.ViewBaseServlet;
import com.atguigu.myssm.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author by Antlers Email:3219599757@qq.com
 * @Description
 * @date 2022/7/23.
 * @package_name com.atguigu.servlets
 **/
@WebServlet("/del.do")
public class DelServlet extends ViewBaseServlet {
    private FruitDao fruitDao = new FruitDaoImpl();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fidStr = req.getParameter("fid");
        if (StringUtil.isNotEmpty(fidStr)){
            int fid = Integer.parseInt(fidStr);
            fruitDao.delFruit(fid);
            resp.sendRedirect("index");
        }

    }
}
