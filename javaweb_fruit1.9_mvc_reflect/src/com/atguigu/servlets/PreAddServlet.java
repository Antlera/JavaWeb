package com.atguigu.servlets;

import com.atguigu.myssm.myspringmvc.ViewBaseServlet;

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
@WebServlet("/preAdd.do")
public class PreAddServlet extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.processTemplate("add",req,resp);
    }
}
