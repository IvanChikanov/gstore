package com.chikanov.gstore.filters;

import com.chikanov.gstore.entity.tgentities.TgUser;
import com.chikanov.gstore.exceptions.ResponseException;
import com.chikanov.gstore.records.AuthData;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TelegramValidateFilter implements Filter {

    @Value("${token.value}")
    private String token;
    private final String key = "WebAppData";

    @Autowired
    Authenticator authenticator;

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException{
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        AuthData auth = authenticator.validation(request.getHeader("Authorization"));
        ObjectMapper om = new ObjectMapper();
        if(auth.statusCode() == HttpStatus.OK){
            request.setAttribute("user", om.readValue(auth.result(), TgUser.class));
            filterChain.doFilter(servletRequest, servletResponse);
        }
        else{
            HttpServletResponse response = (HttpServletResponse) servletResponse;
            response.setStatus(auth.statusCode().value());
            try(PrintWriter pw = response.getWriter()){
                pw.print(auth.result());
                pw.flush();
            }
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


}
