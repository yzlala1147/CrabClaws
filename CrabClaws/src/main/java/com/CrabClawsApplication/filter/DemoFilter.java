package com.CrabClawsApplication.filter;


import javax.servlet.*;
import java.io.IOException;


public class DemoFilter implements Filter {
    @Override //初始化方法 调用一次
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("初始化");
    }

    @Override       //拦截 调用多次//销毁方法 调用一次
    public void destroy() {
        System.out.println("销毁");
    }

    @Override //拦截 调用多次
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("拦截到请求");
        filterChain.doFilter(servletRequest,servletResponse); //放行操作
    }
}
