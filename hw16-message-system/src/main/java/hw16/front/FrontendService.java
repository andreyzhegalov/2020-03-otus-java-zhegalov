package hw16.front;

import ru.otus.messagesystem.client.MessageCallback;
import ru.otus.messagesystem.client.ResultDataType;

public interface FrontendService {
    void saveUser(ResultDataType user, MessageCallback<ResultDataType> dataConsumer);
}
