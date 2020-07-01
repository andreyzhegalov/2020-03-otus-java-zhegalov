package hw12.server;

import java.util.Arrays;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import hw12.core.service.DBServiceUser;
import hw12.helpers.FileSystemHelper;
import hw12.services.TemplateProcessor;
import hw12.services.UserAuthService;
import hw12.servlet.AdminServlet;
import hw12.servlet.AuthorizationFilter;
import hw12.servlet.LoginServlet;
import hw12.servlet.UsersServlet;

public class UsersWebServer implements WebServer {
    private final Server server;
    private final TemplateProcessor templateProcessor;
    private final UserAuthService authService;
    private final DBServiceUser dbServiceUser;
    private static final String START_PAGE_NAME = "index.html";
    private static final String COMMON_RESOURCES_DIR = "static";

    public UsersWebServer(int port, TemplateProcessor templateProcessor, UserAuthService authService, DBServiceUser dbUserService) {
        server = new Server(port);
        this.templateProcessor = templateProcessor;
        this.authService = authService;
        this.dbServiceUser = dbUserService;
    }

    @Override
    public void start() throws Exception {
        if (server.getHandlers().length == 0) {
            initContext();
        }
        server.start();
    }

    @Override
    public void join() throws Exception {
        server.join();
    }

    @Override
    public void stop() throws Exception {
        server.stop();
    }

    private Server initContext() {

        final var resourceHandler = createResourceHandler();
        final var servletContextHandler = createServletContextHandler();

        final var handlers = new HandlerList();
        handlers.addHandler(resourceHandler);
        handlers.addHandler(applySecurity(servletContextHandler, "/admin"));
        // "/api/user/*"));

        server.setHandler(handlers);
        return server;
    }

    private ResourceHandler createResourceHandler() {
        ResourceHandler resourceHandler = new ResourceHandler();
        resourceHandler.setDirectoriesListed(false);
        resourceHandler.setWelcomeFiles(new String[] { START_PAGE_NAME });
        resourceHandler.setResourceBase(FileSystemHelper.localFileNameOrResourceNameToFullPath(COMMON_RESOURCES_DIR));
        return resourceHandler;
    }

    private ServletContextHandler createServletContextHandler() {
        ServletContextHandler servletContextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        servletContextHandler.addServlet(new ServletHolder(new AdminServlet(templateProcessor, dbServiceUser)), "/admin");
        servletContextHandler.addServlet(new ServletHolder(new UsersServlet(dbServiceUser)), "/users");
        return servletContextHandler;
    }

    private ServletContextHandler applySecurity(ServletContextHandler servletContextHandler, String... paths) {
        servletContextHandler.addServlet(new ServletHolder(new LoginServlet(authService)), "/login");
        final AuthorizationFilter authorizationFilter = new AuthorizationFilter();
        Arrays.stream(paths).forEachOrdered(
                path -> servletContextHandler.addFilter(new FilterHolder(authorizationFilter), path, null));
        return servletContextHandler;
    }
}
