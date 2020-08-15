package hw17.messageservice.handler;

import java.nio.channels.SocketChannel;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hw17.server.ClientHandler;
import hw17.server.SocketChannelHelper;
import hw17.server.message.ReciveMessage;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.client.ResultDataType;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageHelper;

public class MessageHandler implements RequestHandler<ResultDataType> {
    private final Logger logger = LoggerFactory.getLogger(MessageHandler.class);
    private final Map<SocketChannel, ClientHandler> clientMap;

    public MessageHandler(Map<SocketChannel, ClientHandler> clientMap) {
        this.clientMap = clientMap;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        final ReciveMessage reciveMessage = MessageHelper.getPayload(msg);
        final String toClientName = reciveMessage.getTo();

        final List<SocketChannel> clientChannelList = clientMap.entrySet().stream()
                .filter(e -> e.getValue().getName().startsWith(toClientName)).map(Map.Entry::getKey)
                .collect(Collectors.toList());

        final var clientChannel = clientChannelList.get(new Random().nextInt(clientChannelList.size()));
        if (!clientChannel.isConnected()) {
            throw new RuntimeException("Client channel is close");
        }

        final String reciveMessageJson = reciveMessage.toJson();
        logger.debug("send to message {}", reciveMessageJson);
        try {
            SocketChannelHelper.send(clientChannel, reciveMessageJson);
        } catch (Exception e) {
            throw new RuntimeException("Failed send message:" + reciveMessageJson);
        }

        return Optional.empty();
    }
}
