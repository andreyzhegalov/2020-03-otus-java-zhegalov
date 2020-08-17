package hw17.messageservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hw17.server.ServerNIO;
import hw17.server.message.ReciveMessage;
import ru.otus.messagesystem.HandlersStore;
import ru.otus.messagesystem.HandlersStoreImpl;
import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.client.CallbackRegistry;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.client.MsClientImpl;
import ru.otus.messagesystem.client.ResultDataType;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageType;

public class MessageService {
    private static final Logger logger = LoggerFactory.getLogger(ServerNIO.class);
    private final HandlersStore handlersStore;
    private final MessageSystem messageSystem;
    private final CallbackRegistry callbackRegistry;

    public MessageService(MessageSystem messageSystem, CallbackRegistry callbackRegistry) {
        this.messageSystem = messageSystem;
        this.callbackRegistry = callbackRegistry;
        this.handlersStore = new HandlersStoreImpl();
    }

    public void addHandler(RequestHandler<? extends ResultDataType> handler) {
        this.handlersStore.addHandler(MessageType.USER_DATA, handler);
    }

    public MsClient addClient(String clientName) {
        logger.info("Add client \"{}\" to message system", clientName);
        final var msClient = new MsClientImpl(clientName, messageSystem, handlersStore, callbackRegistry);
        try {
            messageSystem.addClient(msClient);
        } catch (IllegalArgumentException ex) {
            logger.info("Client \"{}\" already registred in the message system");
        }
        return msClient;
    }

    public void sendMessage(MsClient client, ReciveMessage message) {
        final Message outMsg = client.produceMessage(message.getTo(), message, MessageType.USER_DATA, data -> {
        });
        client.sendMessage(outMsg);
    }

    public void removeClient(String clientName) {
        logger.info("Remove client \"{}\" from message system", clientName);
        messageSystem.removeClient(clientName);
    }
}