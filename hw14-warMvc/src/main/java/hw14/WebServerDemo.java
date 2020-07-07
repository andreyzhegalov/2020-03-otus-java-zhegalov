package hw14;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import hw14.config.AppConfig;
import hw14.server.UsersWebServer;

public class WebServerDemo {

    public static void main(String[] args) throws Exception {
        final ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);
        final var usersWebServer = ctx.getBean(UsersWebServer.class);
        usersWebServer.start();
        usersWebServer.join();
    }

}
