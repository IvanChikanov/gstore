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
import java.util.Arrays;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

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
            String decoded = URLDecoder.decode(auth, StandardCharsets.UTF_8);
            String[] splitted = decoded.replaceAll("&", "\n&").split("&");
            String hash = "";
            SortedSet<String> others = new TreeSet<>();
            for(var s : splitted)
            {
                if(s.contains("hash"))
                {
                    hash = s.split("=")[1];
                    System.out.println(hash);
                }
                else
                {
                    others.add(s);
                }
            }
            String ready = others.stream().collect(Collectors.joining());
            System.out.println(ready);
            String token = getHexHash(this.token, key);
            String data = getHexHash(ready, token);
            System.out.println(token.equals(hash));
            return true;
        }
        catch (NoSuchAlgorithmException| InvalidKeyException ex)
        {
            return false;
        }

    }
    private String getHexHash(String value, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(key.getBytes(), "HmacSHA256");
        hmac.init(secretKey);
        byte[] hashBytes =  hmac.doFinal(value.getBytes());
        StringBuilder sb = new StringBuilder();
        for(byte b : hashBytes)
        {
            sb.append(String.format("%02x", b));
        }
        System.out.println(sb);
        return sb.toString();
    }
}
