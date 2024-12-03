package com.example.msa_exam.order;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomPostFilter implements Filter, Ordered {

    @Value("${server.port}")
    private String serverPort;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletResponse httpResponse = (HttpServletResponse) servletResponse;

        try{
            filterChain.doFilter(servletRequest, servletResponse);
        }finally{
            httpResponse.setHeader("Server-Port", serverPort);
        }
    }

    @Override
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }
}
