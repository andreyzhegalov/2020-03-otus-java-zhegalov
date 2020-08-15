package hw17.server;

import hw17.messageservice.MessageService;
import hw17.server.message.ReciveMessage;
import hw17.server.message.ResponseMessage;
import hw17.server.message.ResponseType;
import ru.otus.messagesystem.client.MsClient;

public class ClientHandler {
    private String clientName = new String();
    private final MessageService messageService;
    private MsClient msClient;

    public ClientHandler(MessageService messageService) {
        this.messageService = messageService;
    }

    public String recive(String data) {
        final var mayBeMessage = ReciveMessage.fromJson(data);
        if (mayBeMessage.isEmpty()) {
            return new ResponseMessage(ResponseType.ERROR).toJson();
        }
        final ReciveMessage message = mayBeMessage.get();

        if (getName().isEmpty()) {
            clientName = message.getFrom();
            addMessageSystemClient(clientName);
            return new ResponseMessage(ResponseType.REGISTRED).toJson();
        }

        messageService.sendMessage(msClient, message);

        return new ResponseMessage(ResponseType.RECIVED).toJson();
    }

    public String getName() {
        return clientName;
    }

    private void addMessageSystemClient(String clientName) {
        msClient = messageService.addClient(clientName);
    }
}
