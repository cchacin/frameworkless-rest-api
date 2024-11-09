package org.acme.app;

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

import java.util.List;
import java.util.UUID;

public class AppServer {

    private static final UsersDao DAO = new UsersDao();
    private static final UsersService SERVICE = new UsersService(
            () -> Mapper.entityToCores(DAO.users()),
            (User.UserId userId) -> DAO.user(userId.value())
                    .map(Mapper::entityToCore)
                    .orElseThrow()
    );
    private static final UsersApi API = new UsersApi(
            UserSerializer::serialize,
            () -> Mapper.coreToDtos(SERVICE.users()),
            (UUID uuid) -> SERVICE.user(User.UserId.of(uuid))
                    .map(Mapper::coreToDto)
                    .orElseThrow()
    );
    private final Server server;

    public AppServer() {
        this(Integer.parseInt(System.getProperty("API_PORT", "8080")));
    }

    public AppServer(int port) {
        server = new Server(new VirtualThreadPool(200));
        server.setStopAtShutdown(true);
        server.addConnector(createConnector(port));
        server.setHandler(createHandler());
    }

    public void start() throws Exception {
        server.start();
    }

    public void stop() throws Exception {
        server.stop();
    }

    public int getPort() {
        return ((ServerConnector)server.getConnectors()[0]).getLocalPort();
    }

    private ServerConnector createConnector(int port) {
        var connector = new ServerConnector(server);
        connector.setName("main");
        connector.setPort(Integer.parseInt(System.getProperty("API_PORT", "8080")));
        return connector;
    }

    private static ServletContextHandler createHandler() {
        var contextHandler = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        contextHandler.addServlet(new ServletHolder(API), "/");
        contextHandler.setVirtualHosts(List.of("@main"));
        return contextHandler;
    }
}
