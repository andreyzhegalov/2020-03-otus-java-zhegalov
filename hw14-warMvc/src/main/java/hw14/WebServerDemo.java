package hw14;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import hw14.config.AppConfig;
import hw14.core.service.DbServiceUserImpl;
import hw14.server.UsersWebServer;
import hw14.services.InitializerService;
import hw14.services.TemplateProcessor;
import hw14.services.TemplateProcessorImpl;
import hw14.services.UserAuthService;
import hw14.services.UserAuthServiceImpl;

public class WebServerDemo {
    private static final String TEMPLATES_DIR = "/templates/";

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Server port not defined. Setup port argument");
            return;
        }

        final ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

        final var dbServiceUser = ctx.getBean(DbServiceUserImpl.class);

        final var initializerService = new InitializerService(dbServiceUser);
        initializerService.prepareUsers();

        final UserAuthService authService = new UserAuthServiceImpl(dbServiceUser);
        final TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        final UsersWebServer usersWebServer = new UsersWebServer(Integer.parseInt(args[0]), templateProcessor,
                authService, dbServiceUser);

        usersWebServer.start();
        usersWebServer.join();
    }

}
