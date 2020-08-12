package hw17.messageservice.handler;

import java.util.Optional;

import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.client.ResultDataType;
import ru.otus.messagesystem.message.Message;

public class MessageHandler implements RequestHandler<ResultDataType> {
    // private final CopyOnWriteArrayList<Pair<String, Socket>> socketList;

    @Override
    public Optional<Message> handle(Message msg) {
        // return Optional.of(MessageBuilder.buildReplyMessage(msg, null));
        return Optional.empty();
    }

}
