package com.chikanov.gstore.filters;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.tomcat.util.digester.DocumentProperties;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class TelegramValidateFilter implements Filter {

    private String token;
    private final String key = "WebAppData";
    private TelegramValidatorHttpRequestWrapper requestWrapper;

    public TelegramValidateFilter(String token){this.token = token;}
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
            System.out.println(URLDecoder.decode(auth, StandardCharsets.UTF_8));
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
