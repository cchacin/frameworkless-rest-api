package org.acme.app;

import org.acme.api.UsersApi;
import org.acme.core.UsersService;
import org.acme.dao.UsersDao;
import org.acme.json.UserSerializer;
import org.eclipse.jetty.ee10.servlet.ServletContextHandler;
import org.eclipse.jetty.ee10.servlet.ServletHolder;
import org.eclipse.jetty.server.Server;

public class Main {
    private static final UsersDao DAO = new UsersDao();
    private static final UsersService SERVICE = new UsersService(
            () -> Mapper.entityToCores(DAO.users())
    );
    private static final UsersApi API = new UsersApi(
            UserSerializer::serialize,
            () -> Mapper.coreToDtos(SERVICE.users())
    );

    public static void main(String[] args) throws Exception {
        var server = new Server(Integer.parseInt(System.getProperty("API_PORT", "8080")));
        var context = new ServletContextHandler(ServletContextHandler.NO_SESSIONS);
        context.addServlet(new ServletHolder(API), "/");
        server.setHandler(context);
        server.setStopAtShutdown(true);
        server.start();
        server.join();
    }
}
