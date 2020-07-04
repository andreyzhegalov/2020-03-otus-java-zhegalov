package hw12;

import hw12.server.UsersWebServer;
import hw12.services.InitializerService;
import hw12.services.TemplateProcessor;
import hw12.services.TemplateProcessorImpl;
import hw12.services.UserAuthService;
import hw12.services.UserAuthServiceImpl;

public class WebServerDemo {
    private static final String TEMPLATES_DIR = "/templates/";

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Server port not defined. Setup port argument");
            return;
        }

        final var dbServiceUser = new InitializerService().getDbUserService();

        final UserAuthService authService = new UserAuthServiceImpl(dbServiceUser);
        final TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        final UsersWebServer usersWebServer = new UsersWebServer(Integer.parseInt(args[0]), templateProcessor,
                authService, dbServiceUser);

        usersWebServer.start();
        usersWebServer.join();

    }

}
