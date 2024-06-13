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

    private final String token;
    private final String key = "WebAppData";
    private TelegramValidatorHttpRequestWrapper requestWrapper;

    public TelegramValidateFilter(String token){this.token = token;}
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        System.out.println(request.getHeader("User-Chat"));
        boolean ok = validate(request.getHeader("Authorization"));
        if(ok)
            filterChain.doFilter(servletRequest, servletResponse);
        else{
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setStatus(401);
        }
    }
    private boolean validate(String auth){
        try {
            String decoded = URLDecoder.decode(auth, StandardCharsets.UTF_8);
            String[] splitted = decoded.split("&");
            String hash = "";
            SortedSet<String> others = new TreeSet<>();
            for(var s : splitted)
            {
                if(s.contains("hash"))
                {
                    hash = s.split("=")[1];
                }
                else
                {
                    others.add(s);
                }
            }
            String ready = others.stream().collect(Collectors.joining("\n"));
            byte[] token = getHash(this.token.getBytes(), key.getBytes());
            byte[] data = getHash(ready.getBytes(), token);
            return getHex(data).equals(hash);
        }
        catch (Exception ex)
        {
            return false;
        }

    }
    private byte[] getHash(byte[] value, byte[] key) throws NoSuchAlgorithmException, InvalidKeyException {
        Mac hmac = Mac.getInstance("HmacSHA256");
        SecretKeySpec secretKey = new SecretKeySpec(key, "HmacSHA256");
        hmac.init(secretKey);
        byte[] hashBytes =  hmac.doFinal(value);
        return hashBytes;
    }
    private String getHex(byte[] byteArr)
    {
        StringBuilder bytes = new StringBuilder();
        for(byte b: byteArr)
        {
            bytes.append(b);
        }
        StringBuilder sb = new StringBuilder();
        for(byte b : byteArr)
        {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
    private boolean checkDataEnable(String decoded, )
    {

    }
}
