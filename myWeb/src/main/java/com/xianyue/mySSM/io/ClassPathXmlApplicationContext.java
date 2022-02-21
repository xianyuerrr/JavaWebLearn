package com.xianyue.mySSM.io;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class ClassPathXmlApplicationContext implements BeanFactory{
    public final Map<String, Object> beanMap = new HashMap<>();
    public String configPath = "application.xml";

    public ClassPathXmlApplicationContext() {
        // 获取所有的bean节点
        NodeList beanNodeList = getBeanNodeList(configPath);

        // 实例化配置文件中的 bean 对象，装入 beanMap 容器
        creatBean(beanNodeList);

        // 组装 bean 之间的依赖关系
        configBean(beanNodeList);
    }

    private NodeList getBeanNodeList(String configPath) {
        //1.创建DocumentBuilderFactory
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        //2.创建DocumentBuilder对象
        DocumentBuilder documentBuilder = null;
        try {
            documentBuilder = documentBuilderFactory.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(configPath);

        //3.创建Document对象
        Document document = null;
        try {
            document = Objects.requireNonNull(documentBuilder).parse(inputStream);
        } catch (SAXException | IOException e) {
            e.printStackTrace();
        }

        if (document != null) {
            return document.getElementsByTagName("bean");
        }
        return null;
    }

    private void creatBean(NodeList beanNodeList) {
        for (int i=0; i < beanNodeList.getLength(); i++) {
            Node beanNode = beanNodeList.item(i);
            if (beanNode instanceof Element) {
                // beanNode.getNodeType() == Node.ELEMENT_NODE
                Element beanElement = (Element) beanNode;
                String beanName = beanElement.getAttribute("id");
                String beanClass = beanElement.getAttribute("class");

                try {
                    Class<?> controllerClass = Class.forName(beanClass);
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

    private void configBean(NodeList beanNodeList) {
        for (int i=0; i < beanNodeList.getLength(); i++) {
            Node beanNode = beanNodeList.item(i);
            if (beanNode instanceof Element) {
                // beanNode.getNodeType() == Node.ELEMENT_NODE
                Element beanElement = (Element) beanNode;

                String beanName = beanElement.getAttribute("id");
                Object beanObj = beanMap.get(beanName);

                NodeList childNodes = beanElement.getChildNodes();
                for (int j=0; j < childNodes.getLength(); j++) {
                    Node childNode = childNodes.item(j);

                    if (childNode instanceof Element) {
                        Element childElement = (Element) childNode;
                        if (childElement.getTagName().equals("property")) {
                            String refName = childElement.getAttribute("name");
                            String ref = childElement.getAttribute("ref");
                            Object refObj = beanMap.get(ref);

                            try {
                                Field field = beanObj.getClass().getDeclaredField(refName);
                                field.setAccessible(true);
                                field.set(beanObj, refObj);
                            } catch (NoSuchFieldException | IllegalAccessException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            }
        }
    }

    @Override
    public Object getBean(String beanName) {
        return beanMap.get(beanName);
    }
}
