package hw16.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hw16.core.service.DBServiceUser;
import hw16.messageservice.DbMessageService;
import hw16.messageservice.FrontMessageService;
import hw16.messageservice.handler.GetUserDataResponseHandler;
import hw16.messageservice.handler.UpdateUsersRequestHandler;
import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.client.CallbackRegistry;
import ru.otus.messagesystem.message.MessageType;

@Configuration
public class MessageSystemConfig {
    @Bean
    public DbMessageService dbMessageService(DBServiceUser dbServiceUser, MessageSystem messageSystem,
            CallbackRegistry callbackRegistry) {
        final var dbMessageService = new DbMessageService(messageSystem, callbackRegistry);
        dbMessageService.addHandler(MessageType.USER_DATA, new UpdateUsersRequestHandler(dbServiceUser));
        return dbMessageService;
    }

    @Bean
    public FrontMessageService frontMessageService(DBServiceUser dbServiceUser, MessageSystem messageSystem,
            CallbackRegistry callbackRegistry) {
        final var frontMessageService = new FrontMessageService(messageSystem, callbackRegistry);
        frontMessageService.addHandler(MessageType.USER_DATA, new GetUserDataResponseHandler(callbackRegistry));
        return frontMessageService;
    }
}
