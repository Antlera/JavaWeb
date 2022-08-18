package com.atguigu.servlets;

import com.atguigu.fruit.dao.impl.FruitDAOImpl;
import com.atguigu.fruit.pojo.Fruit;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author by Antlers Email:3219599757@qq.com
 * @Description
 * @date 2022/7/21.
 * @package_name com.atguigu.servlets
 **/
public class AddServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //post方式下，设置编码，防止中文乱码
        String fname = req.getParameter("fname");
        String priceStr = req.getParameter("price");
        //1.将字符串打散为字节数组
//        fname.getBytes("ISO-8859-1");
        req.setCharacterEncoding("UTF-8");
        int price = Integer.parseInt(priceStr);
        String fcountStr = req.getParameter("fcount");
        int fcount = Integer.parseInt(fcountStr);
        String remark = req.getParameter("remark");

        System.out.println("fname="+fname);
        System.out.println("price="+price);
        System.out.println("fcount="+fcount);
        System.out.println("remark="+remark);
        FruitDAOImpl fruitDAO = new FruitDAOImpl();
        boolean flag = fruitDAO.addFruit(new Fruit(0, fname, price, fcount, remark));
        System.out.println(flag?"添加成功":"添加失败");
    }
}
