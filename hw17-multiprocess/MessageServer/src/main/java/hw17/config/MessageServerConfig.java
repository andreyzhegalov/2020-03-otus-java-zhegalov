package hw17.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hw17.messageservice.MessageService;
import hw17.server.ServerNIO;

@Configuration
public class MessageServerConfig {

    @Bean
    ServerNIO getServerNIO(MessageService messageService){
        return new ServerNIO(messageService);
    }
}

