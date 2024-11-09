package org.acme.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.acme.api.UserDto;

import java.util.List;

public class UserSerializer {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String serialize(Object value) {
        try {
            return MAPPER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
