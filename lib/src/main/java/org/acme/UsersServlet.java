package org.acme;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

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
    private final Supplier<List<User>> getUsers;

    public UsersServlet(
            Function<User, String> mapToJson,
            Supplier<List<User>> getUsers
    ) {
        this.mapToJson = mapToJson;
        this.getUsers = getUsers;
    }

    public UsersServlet() {
        this(
                UserJsonSerializer::serialize,
                () -> USERS
        );
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(
                getUsers.get()
                        .stream()
                        .map(this.mapToJson)
                        .toList()
        );
    }
}