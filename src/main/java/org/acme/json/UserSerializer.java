package org.acme.json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.util.List;
import org.acme.api.UserDto;

public class UserSerializer {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final ObjectWriter USER_LIST_WRITER =
            MAPPER.writerFor(new UserListTypeReference());
    private static final ObjectWriter USER_WRITER = MAPPER.writerFor(UserDto.class);

    public static String serializeUsers(List<UserDto> value) {
        try {
            return USER_LIST_WRITER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static String serializeUser(UserDto value) {
        try {
            return USER_WRITER.writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static class UserListTypeReference extends TypeReference<List<UserDto>> {}
}
