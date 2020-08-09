package hw16.messageservice;

import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.client.CallbackRegistry;
import ru.otus.messagesystem.client.MessageCallback;
import ru.otus.messagesystem.client.ResultDataType;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageType;

public class FrontMessageService extends MessageService {
    private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";

    public FrontMessageService(MessageSystem messageSystem, CallbackRegistry callbackRegistry) {
        super(FRONTEND_SERVICE_CLIENT_NAME, messageSystem, callbackRegistry);
    }

    public void send(String msClientName, ResultDataType data, MessageType msgType,
            MessageCallback<ResultDataType> dataConsumer) {
        Message outMsg = getMsClient().produceMessage(msClientName, data, msgType, dataConsumer);
        getMsClient().sendMessage(outMsg);
    }
}
