package com.chikanov.gstore.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

public class TelegramValidateFilter implements Filter {
    @Value("${token.value}")
    private String token;

    private final String key = "WebAppData";
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
            Mac hmac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
            hmac.init(secretKey);
            byte[] hashBytes =  hmac.doFinal(token.getBytes());
            StringBuilder sb = new StringBuilder();
            for(byte b : hashBytes)
            {
                sb.append(String.format("%02X", b));
            }
            System.out.println(sb);
            return true;
        }
        catch (NoSuchAlgorithmException| InvalidKeyException ex)
        {
            return false;
        }

    }
}
