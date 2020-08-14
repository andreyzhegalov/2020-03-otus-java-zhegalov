package hw17.messageservice.handler;

import static java.util.stream.Collectors.toList;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hw17.server.SocketHandler;
import hw17.server.message.ReciveMessage;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.client.ResultDataType;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageHelper;

public class MessageHandler implements RequestHandler<ResultDataType> {
    private final Logger logger = LoggerFactory.getLogger(MessageHandler.class);
    private final Map<SocketHandler, Socket> clientMap;

    public MessageHandler(Map<SocketHandler, Socket> clientMap) {
        this.clientMap = clientMap;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        final ReciveMessage reciveMessage = MessageHelper.getPayload(msg);
        final String toClientName = reciveMessage.getTo();
        final List<Socket> clientSocketList = clientMap.entrySet().stream()
                .filter(e -> e.getKey().getName().startsWith(toClientName)).map(Map.Entry::getValue).collect(toList());
        final var clientSocket = clientSocketList.get(new Random().nextInt(clientSocketList.size()));
        if(!clientSocket.isConnected()){
            throw new RuntimeException("Client soket is close");
        }

         try (PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)){
             // out.println(reciveMessage.toJson());
             out.flush();
         } catch (Exception ex) {
             logger.error("error", ex);
         }

        return Optional.empty();
    }
}
