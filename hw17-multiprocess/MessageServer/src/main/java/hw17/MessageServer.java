package hw17;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import hw17.config.MessageServerConfig;
import hw17.config.MessageSystemConfig;
import hw17.server.ServerNIO;

public class MessageServer {
    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = new AnnotationConfigApplicationContext(MessageServerConfig.class,
                MessageSystemConfig.class);
        final var server = ctx.getBean(ServerNIO.class);

        server.start(8080);
    }
}
