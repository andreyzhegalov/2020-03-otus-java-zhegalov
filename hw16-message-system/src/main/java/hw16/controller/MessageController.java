package hw16.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import hw16.core.model.User;
import hw16.core.service.DBServiceUser;

@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    private final DBServiceUser dbServiceUser;

    public MessageController(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @MessageMapping("/message")
    @SendTo("/topic/users")
    public void getMessage(User user) {
        logger.info("got new user :{}", user);
        if (!(user.getName().isEmpty() || user.getPassword().isEmpty())) {
            dbServiceUser.saveUser(user);
        }
    }
}
