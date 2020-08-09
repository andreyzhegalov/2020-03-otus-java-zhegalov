package hw16.messageservice;

import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.client.CallbackRegistry;

public class DbMessageService extends MessageService {
    private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";

    public DbMessageService(MessageSystem messageSystem, CallbackRegistry callbackRegistry) {
        super(DATABASE_SERVICE_CLIENT_NAME, messageSystem, callbackRegistry);
    }
}
