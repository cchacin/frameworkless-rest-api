package org.acme;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

@WebServlet(value = "/users/*")
public class UsersServlet extends HttpServlet {
    private final static List<User> USERS = List.of(
            new User("user1", "user1"),
            new User("user2", "user2"),
            new User("user3", "user3"),
            new User("user4", "user4"),
            new User("user5", "user5")
    );

    private final Function<User, String> mapToJson;

    public UsersServlet(Function<User, String> mapToJson) {
        this.mapToJson = mapToJson;
    }

    public UsersServlet() {
        this(UserJsonSerializer::serialize);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(
                USERS.stream()
                        .map(this.mapToJson)
                        .toList()
        );
    }
}