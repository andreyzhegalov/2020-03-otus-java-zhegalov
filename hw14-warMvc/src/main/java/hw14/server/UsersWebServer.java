package hw14.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import hw14.core.service.DBServiceUser;
import hw14.helpers.FileSystemHelper;
import hw14.services.InitializerService;
import hw14.services.TemplateProcessor;
import hw14.servlet.AdminServlet;
import hw14.servlet.LoginServlet;
import hw14.servlet.UsersServlet;

public class UsersWebServer implements WebServer {
    private final Server server;
    private final TemplateProcessor templateProcessor;
    private final DBServiceUser dbServiceUser;
    private static final String ADMIN_URI = "/admin";
    private static final String LOGIN_URI = "/login";
    private static final String USERS_URI = "/users";
    private static final String START_PAGE_NAME = "index.html";
    private static final String COMMON_RESOURCES_DIR = "static";

    public UsersWebServer(int port, TemplateProcessor templateProcessor, InitializerService initializerService,
            DBServiceUser dbUserService) {
        server = new Server(port);
        this.templateProcessor = templateProcessor;
        this.dbServiceUser = dbUserService;

        initializerService.prepareUsers();
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
        handlers.addHandler(servletContextHandler);
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
        servletContextHandler.addServlet(new ServletHolder(new LoginServlet()), LOGIN_URI);
        servletContextHandler.addServlet(new ServletHolder(new AdminServlet(templateProcessor, dbServiceUser)),
                ADMIN_URI);
        servletContextHandler.addServlet(new ServletHolder(new UsersServlet(dbServiceUser)), USERS_URI);
        return servletContextHandler;
    }
}
