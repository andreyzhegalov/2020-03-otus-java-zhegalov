package hw17.controller;

import com.google.gson.Gson;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import hw17.messageclient.MessageClient;
import hw17.model.User;


@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    private final SimpMessagingTemplate template;
    private final MessageClient messageClient;

    public MessageController( MessageClient messageClient, SimpMessagingTemplate template) {
        this.template = template;
        this.messageClient = messageClient;
        this.messageClient.connect();
    }

    @MessageMapping("/newUser")
    public void onNewUser(User user) {
        logger.info("got new user :{}", user);
        final Gson gson = new Gson();
        messageClient.send("front", gson.toJson(user));
    }

    // public void sendUserList(UserListDto userList) {
    //     this.template.convertAndSend("/topic/users", userList.getUserList());
    // }
}
