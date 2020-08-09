package hw16.messageservice;

import ru.otus.messagesystem.HandlersStore;
import ru.otus.messagesystem.HandlersStoreImpl;
import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.client.CallbackRegistry;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.client.MsClientImpl;
import ru.otus.messagesystem.client.ResultDataType;
import ru.otus.messagesystem.message.MessageType;

public class MessageService {
    private final String clientName;
    private final MsClient msClient;
    private final HandlersStore handlersStore;

    MessageService(String clientName, MessageSystem messageSystem, CallbackRegistry callbackRegistry) {
        this.clientName = clientName;
        this.handlersStore = new HandlersStoreImpl();
        this.msClient = new MsClientImpl(clientName, messageSystem, handlersStore, callbackRegistry);
        messageSystem.addClient(msClient);
    }

    public void addHandler(MessageType messageType, RequestHandler<? extends ResultDataType> handler) {
        this.handlersStore.addHandler(messageType, handler);
    }

    public String getClientName() {
        return clientName;
    }

    public MsClient getMsClient() {
        return msClient;
    }
}
