package hw16.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import hw16.core.model.User;
import hw16.core.service.DBServiceUser;
import hw16.dto.UserDto;
import ru.otus.messagesystem.MessageSystem;

@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    private final DBServiceUser dbServiceUser;
    private final MessageSystem messageSystem;

    public MessageController(DBServiceUser dbServiceUser, MessageSystem messageSystem) {
        this.dbServiceUser = dbServiceUser;
        this.messageSystem = messageSystem;
    }

    @MessageMapping("/newUser")
    @SendTo("/topic/users")
    public List<UserDto> onNewUser(User user) {
        logger.info("got new user :{}", user);
        if (!(user.getName().isEmpty() || user.getPassword().isEmpty())) {
            dbServiceUser.saveUser(user);
        }
        return dbServiceUser.getAllUsers().stream().map(u -> new UserDto(u)).collect(Collectors.toList());
    }
}
