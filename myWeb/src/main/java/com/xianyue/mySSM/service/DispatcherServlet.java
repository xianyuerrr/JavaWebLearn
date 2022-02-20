package com.xianyue.mySSM.service;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @auther xianyue
 * @date 2022/2/19 - 星期六 - 14:26
 **/
// 将请求都在此中央处理器拦截
@WebServlet(name = "dispatcher", value = "/*")
public class DispatcherServlet extends ViewBaseServlet{
    private final Map<String, Object> beanMap = new HashMap<>();


    public DispatcherServlet() {

    }


    public void init() throws ServletException {
        super.init();
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("application.xml");
        //1.创建DocumentBuilderFactory
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        //2.创建DocumentBuilder对象
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        //3.创建Document对象
        Document document = null;
        try {
            document = documentBuilder.parse(inputStream);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //4.获取所有的bean节点
        NodeList beanNodeList = document.getElementsByTagName("bean");

        for (int i=0; i<beanNodeList.getLength(); i++) {
            Node beanNode = beanNodeList.item(i);
            if (beanNode instanceof Element) {
                // beanNode.getNodeType() == Node.ELEMENT_NODE
                Element beanElement = (Element) beanNode;
                String beanName = beanElement.getAttribute("name");
                String beanClass = beanElement.getAttribute("class");

                try {
                    Class controllerClass = Class.forName(beanClass);
                    Object controllerObj = controllerClass
                            .getDeclaredConstructor()
                            .newInstance();
                    beanMap.put(beanName, controllerObj);
                } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("utf8");

        String beanName = req.getServletPath() + req.getPathInfo();
        // 去掉最开始的 "/"
        beanName = beanName.substring(1);

        // 根据 beanName 获取对应 Controller 对象
        Object controllerObj = beanMap.get(beanName);

        // 对于拦截到的诸如 index.css, index.js 之类的请求，对此类请求不进行特殊处理
        // 获取其请求的文件，将其写入 response 返回
        if (controllerObj == null) {
            String path = this.getServletContext().getRealPath("/") + beanName;
            InputStream inputStream = new FileInputStream(path);
            PrintWriter writer = resp.getWriter();
            writer.write(new String(inputStream.readAllBytes()));
            writer.close();
            return;
        }


        String operation = req.getParameter("operation");

        if (operation == null || "".equals(operation)) {
            operation = "index";
        }

        try {
           // -parameters
            Method method = controllerObj
                    .getClass()
                    .getDeclaredMethod(operation, HttpServletRequest.class, HttpServletResponse.class);
            method.setAccessible(true);
            String methodRet = String.valueOf(method.invoke(controllerObj, req, resp));

            if (methodRet == null || "".equals(methodRet)) {
                methodRet = "error";
            }
            // e.g. "redirect:grade&processTemplate:update"
            // 可以同时有多个操作（只是举个例子，这两种操作是冲突的）
            String [] opers = methodRet.split("&");
            for (String oper : opers) {
                String[] split = oper.split(":");
                // 假设操作都有一个参数
                switch (split[0]) {
                    case "redirect" -> resp.sendRedirect(split[1]);
                    case "processTemplate" -> super.processTemplate(split[1], req, resp);
                    default -> System.out.println("未知的操作");
                }
            }

            // 由于 DispatcherServlet 接管了所有请求，原来的 GradeServlet 已经变成了 GradeController，不再能获取到网页上下文
            // ViewBaseServlet 中对 th 模板的解析处理中使用到的 getServletContext() 函数也不在能正确获取到网页上下文

            // 可以使用 DispatcherServlet 获取完上下文对 GradeController 进行赋值，但是这样比较麻烦，且增大了 DispatcherServlet 与
            // 各个 Controller 的耦合度。存在更优的选择
            // 所以 GradeController 已经没有必要继承 ViewBaseServlet，可以由 DispatcherServlet 对模板解析进行接管
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
