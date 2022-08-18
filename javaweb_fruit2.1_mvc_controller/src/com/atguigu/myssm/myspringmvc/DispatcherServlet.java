package com.atguigu.myssm.myspringmvc;

import com.atguigu.myssm.util.StringUtil;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * @author by Antlers Email:3219599757@qq.com
 * @Description
 * @date 2022/7/24.
 * @package_name com.atguigu.myssm.myspringmvc
 **/
@WebServlet("*.do")
public class DispatcherServlet extends ViewBaseServlet{

    private Map<String,Object> beanMap = new HashMap<>();
    public DispatcherServlet(){
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("applicationContext.xml");
            DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = documentBuilderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(is);
            NodeList beanNodeList = document.getElementsByTagName("bean");
            for(int i = 0;i<beanNodeList.getLength();i++){
                Node beanNode = beanNodeList.item(i);
                if(beanNode.getNodeType() == Node.ELEMENT_NODE){
                    Element beanElement = (Element) beanNode;
                    String beanId = beanElement.getAttribute("id");
                    String beanClassName = beanElement.getAttribute("class");
                    Object beanObj = Class.forName(beanClassName).newInstance();
                    beanMap.put(beanId,beanObj);
                }
            }
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String servletPath = req.getServletPath();
        System.out.println("servletPath = " + servletPath);
        int lastDotIndex = servletPath.lastIndexOf('.');
        servletPath = servletPath.substring(1,lastDotIndex);
        System.out.println("servletPathSub = " + servletPath);
        Object controllerBeanObj = beanMap.get(servletPath);
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
