package hw17.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import hw17.model.User;


@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    private final SimpMessagingTemplate template;

    public MessageController( MessageClient messageClient, SimpMessagingTemplate template) {
        this.template = template;
    }

    @MessageMapping("/newUser")
    public void onNewUser(User user) {
        logger.info("got new user :{}", user);

        // frontMessageService.send(dbMessageService.getClientName(), (ResultDataType) new UserSecureDto(user),
        //         MessageType.USER_DATA, data -> {
        //             sendUserList((UserListDto) data);
        //         });
    }

    // public void sendUserList(UserListDto userList) {
    //     this.template.convertAndSend("/topic/users", userList.getUserList());
    // }
}
