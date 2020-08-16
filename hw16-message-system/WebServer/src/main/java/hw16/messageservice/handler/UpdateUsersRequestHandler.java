package hw16.messageservice.handler;

import java.util.Optional;
import java.util.stream.Collectors;

import hw16.core.service.DBServiceUser;
import hw16.dto.UserDto;
import hw16.dto.UserListDto;
import hw16.dto.UserSecureDto;
import ru.otus.messagesystem.RequestHandler;
import ru.otus.messagesystem.client.ResultDataType;
import ru.otus.messagesystem.message.Message;
import ru.otus.messagesystem.message.MessageBuilder;
import ru.otus.messagesystem.message.MessageHelper;

public class UpdateUsersRequestHandler implements RequestHandler<ResultDataType> {
    private final DBServiceUser dbServiceUser;

    public UpdateUsersRequestHandler(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    public Optional<Message> handle(Message msg) {
        final UserSecureDto userSecureDto = MessageHelper.getPayload(msg);
        if (!(userSecureDto.getName().isEmpty() || userSecureDto.getPassword().isEmpty())) {
            dbServiceUser.saveUser(userSecureDto.toUser());
        }
        final var allUsersDto = dbServiceUser.getAllUsers().stream().map(u -> new UserDto(u))
                .collect(Collectors.toList());
        return Optional.of(MessageBuilder.buildReplyMessage(msg, new UserListDto(allUsersDto)));
    }
}
