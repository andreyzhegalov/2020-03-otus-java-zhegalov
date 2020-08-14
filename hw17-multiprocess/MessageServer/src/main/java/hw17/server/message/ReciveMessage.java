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
        final var gson = new Gson();
        ReciveMessage message = null;
        try {
            message = gson.fromJson(jsonString, ReciveMessage.class);
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
        return (message != null) ? Optional.of(message): Optional.empty();
    }

    public String toJson(){
        final var gson = new Gson();
        return gson.toJson(this, ReciveMessage.class);
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

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (from != null ? from.hashCode() : 0);
        result = 31 * result + (to != null ? to.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ReciveMessage object = (ReciveMessage) o;

        if (from != null ? !from.equals(object.from) : object.from != null) return false;
        if (to != null ? !to.equals(object.to) : object.to != null) return false;
        return !(data != null ? !data.equals(object.data) : object.data != null);
    }
}




