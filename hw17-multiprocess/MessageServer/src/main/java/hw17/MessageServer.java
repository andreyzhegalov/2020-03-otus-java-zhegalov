package hw17;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import hw17.config.MessageServerConfig;
import hw17.server.Server;

public class MessageServer {
    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(MessageServerConfig.class);
        final Server server  = ctx.getBean(Server.class);

        server.start(8080);
    }
}
