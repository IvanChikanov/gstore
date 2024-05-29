package com.chikanov.gstore.filters;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.util.StreamUtils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class TelegramValidatorHttpRequestWrapper extends HttpServletRequestWrapper {
    private byte[] validBody;
    private String authenticateData;
    private ObjectMapper om;
    public TelegramValidatorHttpRequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        String buffBody = StreamUtils.copyToString(request.getInputStream(), Charset.defaultCharset());
        System.out.println(buffBody);
        om = new ObjectMapper();
        JsonNode json = om.readTree(buffBody);
        authenticateData = json.get("auth").asText();
        ObjectNode objectNode = (ObjectNode) json;
        objectNode.remove("auth");
        validBody = om.writeValueAsBytes(objectNode);
    }
    public String getValidData()
    {
        return authenticateData;
    }
    @Override
    public ServletInputStream getInputStream(){
        return new TelegramBodyInputStream(this.validBody);
    }
    @Override
    public BufferedReader getReader(){
        ByteArrayInputStream byteArr = new ByteArrayInputStream(this.validBody);
        return new BufferedReader(new InputStreamReader(byteArr));
    }
}
