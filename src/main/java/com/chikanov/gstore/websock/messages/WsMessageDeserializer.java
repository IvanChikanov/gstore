package com.chikanov.gstore.websock.messages;

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
        Auth auth = objectMapper.treeToValue(node.get("payload"), Auth.class);
        return new WsMessage<>(node.get("type").asText(), node.get("from").asText(), UUID.fromString(node.get("game").asText()), auth);
    }
}
