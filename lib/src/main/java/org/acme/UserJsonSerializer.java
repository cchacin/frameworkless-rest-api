package org.acme;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class UserJsonSerializer {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    public static String serialize(User user) {
        try {
            return MAPPER.writeValueAsString(user);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
