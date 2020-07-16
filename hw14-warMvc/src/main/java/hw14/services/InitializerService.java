package hw14.services;

import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import hw14.core.model.User;
import hw14.core.service.DBServiceUser;

@Service
public class InitializerService {
    private final DBServiceUser dbServiceUser;

    public InitializerService(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    public void prepareUsers() {
        final var admin = new User("admin");
        admin.setPassword("11111");
        dbServiceUser.saveUser(admin);
        final var user1 = new User("user1");
        user1.setPassword("22222");
        dbServiceUser.saveUser(user1);
    }

    @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        prepareUsers();
    }
}
