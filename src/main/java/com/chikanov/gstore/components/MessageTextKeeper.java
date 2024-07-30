package com.chikanov.gstore.components;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class MessageTextKeeper {

    public Map<String, String> ru;

    public MessageTextKeeper() throws IOException
    {
        ObjectMapper om = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource("texts/ru.json");
        ru = om.readValue(resource.getInputStream(), new TypeReference<>() {});
    }
}
