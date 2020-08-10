package hw17.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hw17.server.Server;

@Configuration
public class MessageServerConfig {

    @Bean
    Server getServer(){
        return new Server();
    }
}

