package hw16.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import hw16.core.model.User;
import hw16.dto.UserListDto;
import hw16.dto.UserSecureDto;
import hw16.messageservice.DbMessageService;
import hw16.messageservice.FrontMessageService;
import ru.otus.messagesystem.client.ResultDataType;
import ru.otus.messagesystem.message.MessageType;

@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    private final FrontMessageService frontMessageService;
    private final DbMessageService dbMessageService;
    private final SimpMessagingTemplate template;

    public MessageController(DbMessageService dbMessageService, FrontMessageService frontMessageService,
            SimpMessagingTemplate template) {
        this.template = template;
        this.dbMessageService = dbMessageService;
        this.frontMessageService = frontMessageService;
    }

    @MessageMapping("/newUser")
    public void onNewUser(User user) {
        logger.info("got new user :{}", user);

        frontMessageService.send(dbMessageService.getClientName(), (ResultDataType) new UserSecureDto(user),
                MessageType.USER_DATA, data -> {
                    sendUserList((UserListDto) data);
                });
    }

    public void sendUserList(UserListDto userList) {
        this.template.convertAndSend("/topic/users", userList.getUserList());
    }
}
