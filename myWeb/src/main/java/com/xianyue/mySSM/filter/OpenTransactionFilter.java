package com.xianyue.mySSM.filter;

import com.xianyue.mySSM.tran.TransactionManager;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.sql.SQLException;

@WebFilter("*")
public class OpenTransactionFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
        try {
            // 开启事务
            TransactionManager.beginTransaction();
            System.out.println("开启");

            //放行
            filterChain.doFilter(servletRequest,servletResponse);

            // 提交事务
            TransactionManager.commitTransaction();
            System.out.println("提交");
        } catch (Exception e) {
            e.printStackTrace();
            try {
                TransactionManager.rollbackTransactions();
                System.out.println("回滚");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @Override
    public void destroy() {

    }
}
