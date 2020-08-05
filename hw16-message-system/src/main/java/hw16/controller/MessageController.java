package hw16.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import hw16.core.model.User;
import hw16.core.service.DBServiceUser;
import hw16.dto.UserListDto;
import hw16.dto.UserSecureDto;
import hw16.front.FrontendService;
import hw16.front.FrontendServiceImpl;
import hw16.front.handlers.GetUserDataResponseHandler;
import hw16.messageservice.DbMessageService;
import hw16.messageservice.handler.UpdateUsersRequestHandler;
import ru.otus.messagesystem.HandlersStore;
import ru.otus.messagesystem.HandlersStoreImpl;
import ru.otus.messagesystem.MessageSystem;
import ru.otus.messagesystem.client.CallbackRegistry;
import ru.otus.messagesystem.client.CallbackRegistryImpl;
import ru.otus.messagesystem.client.MsClient;
import ru.otus.messagesystem.client.MsClientImpl;
import ru.otus.messagesystem.message.MessageType;

@Controller
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);

    private final MessageSystem messageSystem;
    private final CallbackRegistry callbackRegistry;
    private final FrontendService frontendService;

    private final SimpMessagingTemplate template;

    private static final String FRONTEND_SERVICE_CLIENT_NAME = "frontendService";

    public MessageController(DbMessageService dbMessageService, DBServiceUser dbServiceUser,
            MessageSystem messageSystem, SimpMessagingTemplate template) {
        this.messageSystem = messageSystem;
        this.template = template;

        dbMessageService.addHandler(MessageType.USER_DATA, new UpdateUsersRequestHandler(dbServiceUser));

        callbackRegistry = new CallbackRegistryImpl();

        HandlersStore requestHandlerFrontendStore = new HandlersStoreImpl();
        requestHandlerFrontendStore.addHandler(MessageType.USER_DATA, new GetUserDataResponseHandler(callbackRegistry));

        MsClient frontendMsClient = new MsClientImpl(FRONTEND_SERVICE_CLIENT_NAME, this.messageSystem,
                requestHandlerFrontendStore, callbackRegistry);
        frontendService = new FrontendServiceImpl(frontendMsClient, dbMessageService.getClientName());
        this.messageSystem.addClient(frontendMsClient);
    }

    @MessageMapping("/newUser")
    public void onNewUser(User user) {
        logger.info("got new user :{}", user);

        frontendService.saveUser(new UserSecureDto(user), data -> {
            sendUserList((UserListDto) data);
        });
    }

    public void sendUserList(UserListDto userList) {
        this.template.convertAndSend("/topic/users", userList.getUserList());
    }
}
