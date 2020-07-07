package hw14.server;

import java.util.Arrays;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import hw14.core.service.DBServiceUser;
import hw14.helpers.FileSystemHelper;
import hw14.services.TemplateProcessor;
import hw14.services.UserAuthService;
import hw14.servlet.AdminServlet;
import hw14.servlet.AuthorizationFilter;
import hw14.servlet.LoginServlet;
import hw14.servlet.UsersServlet;

public class UsersWebServer implements WebServer {
    private final Server server;
    private final TemplateProcessor templateProcessor;
    private final UserAuthService authService;
    private final DBServiceUser dbServiceUser;
    private static final String ADMIN_URI = "/admin";
    private static final String LOGIN_URI = "/login";
    private static final String USERS_URI = "/users";
    private static final String START_PAGE_NAME = "index.html";
    private static final String COMMON_RESOURCES_DIR = "static";

    public UsersWebServer(int port, TemplateProcessor templateProcessor, UserAuthService authService,
            DBServiceUser dbUserService) {
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
        handlers.addHandler(applySecurity(servletContextHandler, ADMIN_URI, USERS_URI));
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
        servletContextHandler.addServlet(new ServletHolder(new AdminServlet(templateProcessor, dbServiceUser)),
                ADMIN_URI);
        servletContextHandler.addServlet(new ServletHolder(new UsersServlet(dbServiceUser)), USERS_URI);
        return servletContextHandler;
    }

    private ServletContextHandler applySecurity(ServletContextHandler servletContextHandler, String... paths) {
        servletContextHandler.addServlet(new ServletHolder(new LoginServlet(authService)), LOGIN_URI);
        final AuthorizationFilter authorizationFilter = new AuthorizationFilter();
        Arrays.stream(paths).forEachOrdered(
                path -> servletContextHandler.addFilter(new FilterHolder(authorizationFilter), path, null));
        return servletContextHandler;
    }
}
