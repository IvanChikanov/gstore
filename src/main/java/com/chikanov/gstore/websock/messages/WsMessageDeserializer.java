package com.chikanov.gstore.websock.messages;

import com.chikanov.gstore.exceptions.ResponseException;
import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;
import java.util.UUID;

public class WsMessageDeserializer extends StdDeserializer<WsMessage<?>> {

    private ObjectMapper objectMapper = new ObjectMapper();

    public WsMessageDeserializer()
    {
        this(null);
    }

    public WsMessageDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public WsMessage<?> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        JsonNode node = jsonParser.getCodec().readTree(jsonParser);
        switch (node.get("type").asText()){
            case "auth" -> {
                return new WsMessage<>(node.get("type").asText(),
                        node.get("from").asText(),
                        UUID.fromString(node.get("game").asText()),
                        objectMapper.treeToValue(node.get("payload"), Auth.class));
            }
            default ->
            {
                return null;
            }
        }
    }
}
