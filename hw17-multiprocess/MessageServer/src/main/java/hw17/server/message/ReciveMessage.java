package hw17.server.message;

import java.util.Optional;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import ru.otus.messagesystem.client.ResultDataType;

public class ReciveMessage extends ResultDataType {
    private final String from;
    private final String to;
    private final String data;

    public ReciveMessage(String from, String to, String data) {
        this.from = from;
        this.to = to;
        this.data = data;
    }

    public static Optional<ReciveMessage> fromJson(String jsonString){
        final Gson gson = new Gson();
        ReciveMessage message = null;
        try {
            message = gson.fromJson(jsonString, ReciveMessage.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return (message != null) ? Optional.of(message): Optional.empty();
    }

    public String getData() {
        return data;
    }

    public String getTo() {
        return to;
    }

    public String getFrom() {
        return from;
    }
}

