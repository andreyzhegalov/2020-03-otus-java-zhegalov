package hw17.messageservice.handler;

import java.util.Optional;

import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.client.ResultDataType;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageBuilder;

public class UpdateUsersRequestHandler implements RequestHandler<ResultDataType> {

    public UpdateUsersRequestHandler() {
    }

    @Override
    public Optional<Message> handle(Message msg) {
        return Optional.of(MessageBuilder.buildReplyMessage(msg, null));
    }
}
