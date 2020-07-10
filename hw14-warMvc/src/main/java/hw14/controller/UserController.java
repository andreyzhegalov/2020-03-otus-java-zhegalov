package hw14.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import hw14.core.model.User;
import hw14.core.service.DBServiceUser;

@Controller
public class UserController {
    private final DBServiceUser dbServiceUser;

    public UserController(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @PostMapping("/users")
    public RedirectView userSave(@ModelAttribute User user) {
        if (!(user.getName().isEmpty() || user.getPassword().isEmpty())) {
            dbServiceUser.saveUser(user);
        }
        return new RedirectView("/admin", true);
    }
}
