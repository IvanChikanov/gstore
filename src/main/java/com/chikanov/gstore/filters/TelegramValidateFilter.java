package com.chikanov.gstore.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

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
    private boolean validate(String auth){
        try {
            System.out.println(auth);
            Mac hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec("WebAppData".getBytes(), "HmacSHA256");
            hmac.init(secretKey);
            byte[] hashBytes = hmac.doFinal(auth.getBytes());
            System.out.println(Arrays.toString(Base64.getEncoder().encode(hashBytes)));
            return true;
        }
        catch (NoSuchAlgorithmException| InvalidKeyException ex)
        {
            return false;
        }

    }
}
