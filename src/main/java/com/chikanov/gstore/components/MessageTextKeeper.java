package com.chikanov.gstore.components;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
public class MessageTextKeeper {

    private final Map<String, HashMap<String, String>> texts = new HashMap<>();

    public MessageTextKeeper() throws IOException
    {
        ObjectMapper om = new ObjectMapper();
        ClassPathResource resource = new ClassPathResource("texts/ru.json");
        texts.put("ru", om.readValue(resource.getInputStream(), new TypeReference<>() {}));
    }

    public Map<String, String> lang(String lang)
    {
        return texts.get(lang);
    }
}
