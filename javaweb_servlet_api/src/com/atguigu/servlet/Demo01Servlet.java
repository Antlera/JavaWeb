package com.atguigu.servlet;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

/**
 * @author by Antlers Email:3219599757@qq.com
 * @Description
 * @date 2022/7/26.
 * @package_name com.atguigu.servlet
 **/
public class Demo01Servlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        ServletConfig config = getServletConfig();
        String hello = config.getInitParameter("hello");
        System.out.println(hello);
    }
}

//Servlet生命周期：实例化，初始化，服务，销毁
