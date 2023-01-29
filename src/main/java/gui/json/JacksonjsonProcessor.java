package gui.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonjsonProcessor {
private static final ObjectMapper objectMapper = new ObjectMapper();

public static  String toJson(JoomlaPost joomlaPost){
    try {
        return  objectMapper.writeValueAsString(joomlaPost);
    } catch (JsonProcessingException e) {
        throw new RuntimeException(e);
    }
}
}

