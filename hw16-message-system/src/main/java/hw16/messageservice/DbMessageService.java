package hw16.messageservice;

import org.springframework.stereotype.Service;

import ru.otus.messagesystem.HandlersStore;
import ru.otus.messagesystem.HandlersStoreImpl;
import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.client.CallbackRegistry;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.client.MsClientImpl;
import ru.otus.messagesystem.client.ResultDataType;
import ru.otus.messagesystem.message.MessageType;

@Service
public class DbMessageService {
    private static final String DATABASE_SERVICE_CLIENT_NAME = "databaseService";
    private final MsClient msClient;
    private final HandlersStore handlersStore;

    DbMessageService(MessageSystem messageSystem, CallbackRegistry callbackRegistry) {
        this.handlersStore = new HandlersStoreImpl();
        this.msClient = new MsClientImpl(DATABASE_SERVICE_CLIENT_NAME, messageSystem, handlersStore, callbackRegistry);
        messageSystem.addClient(msClient);
    }

    public void addHandler(MessageType messageType, RequestHandler<? extends ResultDataType> handler) {
        this.handlersStore.addHandler(messageType, handler);
    }

    public String getClientName(){
        return DATABASE_SERVICE_CLIENT_NAME;
    }
}
