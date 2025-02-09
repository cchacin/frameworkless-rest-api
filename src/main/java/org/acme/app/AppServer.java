package org.acme.app;

import java.util.List;
import java.util.UUID;
import org.acme.api.UsersApi;
import org.acme.core.User;
import org.acme.core.UsersService;
import org.acme.dao.UsersDao;
import org.acme.json.UserSerializer;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.servlet.ServletHolder;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.util.thread.VirtualThreadPool;

public class AppServer {

    private final Server server;

    public AppServer() {
        this(Integer.parseInt(System.getProperty("API_PORT", "8080")));
    }

    public AppServer(int port) {
        server = createServer(port);
    }

    private static UsersApi createUsersAPI() {
        var usersDao = new UsersDao();
        var usersService =
                new UsersService(
                        () -> Mapper.entityToCores(usersDao.users()),
                        (User.UserId userId) ->
                                usersDao.user(userId.value())
                                        .map(Mapper::entityToCore)
                                        .orElseThrow());
        return new UsersApi(
                UserSerializer::serializeUser,
                UserSerializer::serializeUsers,
                () -> Mapper.coreToDtos(usersService.users()),
                (UUID uuid) ->
                        usersService
                                .user(User.UserId.of(uuid))
                                .map(Mapper::coreToDto)
                                .orElseThrow());
    }

    private Server createServer(int port) {
        var server = new Server(new VirtualThreadPool());
        server.setStopAtShutdown(true);
        server.addConnector(createConnector(port, server));
        try {
            server.setHandler(createHandler(createUsersAPI()));
        } catch (Exception exception) {
            System.out.println("exception = " + exception.getMessage());
        }
        return server;
    }

    public void start() throws Exception {
        server.start();
    }

    public void stop() throws Exception {
        server.stop();
    }

    public int getPort() {
        return ((ServerConnector) server.getConnectors()[0]).getLocalPort();
    }

    private ServerConnector createConnector(int port, Server server) {
        var connector = new ServerConnector(server);
        connector.setName("main");
        connector.setPort(port);
        return connector;
    }

    private ServletContextHandler createHandler(UsersApi usersApi) {
        var contextHandler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        contextHandler.addServlet(new ServletHolder(usersApi), "/");
        contextHandler.setVirtualHosts(List.of("@main"));
        return contextHandler;
    }
}
