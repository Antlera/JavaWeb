package com.atguigu.myssm.myspringmvc;

import com.atguigu.myssm.io.BeanFactory;
import com.atguigu.myssm.io.ClassPathXmlApplicationContext;
import com.atguigu.myssm.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @author by Antlers Email:3219599757@qq.com
 * @Description Servlet调配分发，根据servletPath调用对应controller
 * @date 2022/7/24.
 * @package_name com.atguigu.myssm.myspringmvc
 **/
@WebServlet("*.do")
public class DispatcherServlet extends ViewBaseServlet{
    private BeanFactory beanFactory;
    public DispatcherServlet(){
    }

    @Override
    public void init() throws ServletException {
        super.init();
        beanFactory = new ClassPathXmlApplicationContext();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String servletPath = req.getServletPath();
        System.out.println("servletPath = " + servletPath);
        int lastDotIndex = servletPath.lastIndexOf('.');
        servletPath = servletPath.substring(1,lastDotIndex);
        System.out.println("servletPathSub = " + servletPath);
        Object controllerBeanObj = beanFactory.getBean(servletPath);
        //获取operate参数
        String operate = req.getParameter("operate");
        if(StringUtil.isEmpty(operate)){
            operate="index";
        }
        //获取当前类中所有的方法
//        Method method = null;
        try {
//            method = controllerBeanObj.getClass().getDeclaredMethod(operate, HttpServletRequest.class);
            Method[] methods = controllerBeanObj.getClass().getDeclaredMethods();
            for (Method method:methods ){
                if (operate.equals(method.getName())){
                    //1.统一获取请求参数
                    //获取当前方法的参数，返回参数数组
                    Parameter[] parameters = method.getParameters();
                    //数组存放参数值
                    Object[] parameterValues = new Object[parameters.length];
                    for (int i = 0; i < parameters.length; i++) {
                        Parameter parameter = parameters[i];
                        String parameterName = parameter.getName();
                        if("req".equals(parameterName)){
                            parameterValues[i]=req;
                        } else if ("resp".equals(parameterName)) {
                            parameterValues[i]=resp;
                        }else if("session".equals(parameterName)){
                            parameterValues[i]=req.getSession();
                        }else {
                            String parameterValue = req.getParameter(parameterName);
                            String typeName = parameter.getType().getName();
                            Object parameterObj = parameterValue;
                            if (parameterObj!=null){
                                if("java.lang.Integer".equals(typeName)){
                                    parameterObj = Integer.parseInt(parameterValue);
                                }
                            }
                            parameterValues[i] = parameterObj;
                        }
                    }
                    //2.controller组件方法调用
                    try {
                        method.setAccessible(true);
                        Object returnObj = method.invoke(controllerBeanObj, parameterValues);
                        String methodReturnStr = "";
                        if (returnObj!=null){
                            methodReturnStr = (String) returnObj;
                        }
                        if(methodReturnStr.startsWith("redirect:")){
                            String redirectStr = methodReturnStr.substring("redirect:".length());
                            resp.sendRedirect(redirectStr);
                        }
                        else {
                            super.processTemplate(methodReturnStr,req,resp);
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                    //3.视图处理
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
