package org.acme.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.acme.api.UserDto;

import java.util.List;

public class UserSerializer {

    private static final ObjectWriter MAPPER = new ObjectMapper()
            .writerFor(new TypeReference<List<UserDto>>() {
    });

    public static String serialize(List<UserDto> users) {
        try {
            return MAPPER.writeValueAsString(users);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
