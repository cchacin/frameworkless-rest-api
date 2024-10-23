package org.acme.api;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@WebServlet(value = "/users/*")
public class UsersApi extends HttpServlet {

    private final Function<List<UserDto>, String> mapToJson;
    private final Supplier<List<UserDto>> getUsers;

    public UsersApi(
            Function<List<UserDto>, String> mapToJson,
            Supplier<List<UserDto>> getUsers
    ) {
        this.mapToJson = mapToJson;
        this.getUsers = getUsers;
    }

    protected void doGet(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        response.setContentType("application/json");
        try {
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().print(
                    this.mapToJson.apply(getUsers.get())
            );
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().print("""
                    {
                        "status": 500,
                        "error": "Internal Server Error"
                    }
                    """);
        }

    }

}
