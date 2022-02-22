package com.xianyue.mySSM.listener;

import com.xianyue.mySSM.io.BeanFactory;
import com.xianyue.mySSM.io.ClassPathXmlApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener("")
public class ContextLoaderLister implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContextListener.super.contextInitialized(sce);

        ServletContext application = sce.getServletContext();
        BeanFactory beanFactory = new ClassPathXmlApplicationContext();
        application.setAttribute("beanFactory", beanFactory);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ServletContextListener.super.contextDestroyed(sce);
    }
}
