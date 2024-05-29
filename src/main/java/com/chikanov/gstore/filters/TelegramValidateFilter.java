package com.chikanov.gstore.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Order(1)
public class TelegramValidateFilter implements Filter {
    private TelegramValidatorHttpRequestWrapper requestWrapper;
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        boolean ok = true;
        boolean post = request.getMethod().equals("POST");
        if(post)
        {
            requestWrapper = new TelegramValidatorHttpRequestWrapper(request);
            ok = validate(requestWrapper.getValidData());
        }
        if(ok)
            filterChain.doFilter(post ? requestWrapper:request, servletResponse);
        else{
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setStatus(401);
        }
    }
    private boolean validate(String auth)
    {
        System.out.println(auth);
        return true;
    }
}
