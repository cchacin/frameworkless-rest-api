package org.acme.api;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.regex.Pattern;

@WebServlet(value = "/users/*")
public class UsersApi extends HttpServlet {

    public static final Pattern USERS_UUID_PATTERN =
            Pattern.compile("^(/users/)([0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12})(/[^/]*)?$");
    public static final Pattern USERS_PATTERN =
            Pattern.compile("^(/users)(/)?$");
    private final Function<UserDto, String> mapUserToJson;
    private final Function<List<UserDto>, String> mapUsersToJson;
    private final Supplier<List<UserDto>> getUsers;
    private final Function<UUID, UserDto> getUser;

    public UsersApi(
            Function<UserDto, String> mapUserToJson,
            Function<List<UserDto>, String> mapUsersToJson,
            Supplier<List<UserDto>> getUsers,
            Function<UUID, UserDto> getUser
    ) {
        this.mapUserToJson = mapUserToJson;
        this.mapUsersToJson = mapUsersToJson;
        this.getUsers = getUsers;
        this.getUser = getUser;
    }

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        response.setContentType("application/json");
        var path = request.getServletPath();
        var USERS_UUID_MATCHER = USERS_UUID_PATTERN.matcher(path);
        var USERS_MATCHER = USERS_PATTERN.matcher(path);
        if (USERS_UUID_MATCHER.find()) {
            writeBody(response, () -> this.mapUserToJson.apply(
                            getUser.apply(UUID.fromString(USERS_UUID_MATCHER.group(2)))
                    )
            );
            return;
        }
        if (USERS_MATCHER.find()) {
            writeBody(response, () -> this.mapUsersToJson.apply(getUsers.get()));
            return;
        }
        writeNotFound(response);
    }

    private static void writeBody(
            HttpServletResponse response,
            Supplier<String> responseBody)
            throws IOException {
        try {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().print(
                    responseBody.get()
            );
        } catch (Exception e) {
            writeError(response);
        }
    }

    private static void writeNotFound(
            HttpServletResponse response)
            throws IOException {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        response.getWriter().print("""
                {
                    "status": 404,
                    "error": "Not Found"
                }
                """);
    }

    private static void writeError(
            HttpServletResponse response)
            throws IOException {
        response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        response.getWriter().print("""
                {
                    "status": 500,
                    "error": "Internal Server Error"
                }
                """);
    }
}
