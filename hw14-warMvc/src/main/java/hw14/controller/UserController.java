package hw14.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import hw14.core.service.DBServiceUser;
import hw14.dto.UserDto;

@Controller
public class UserController {
    private final DBServiceUser dbServiceUser;

    public UserController(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @PostMapping("/users")
    public RedirectView userSave(@ModelAttribute UserDto userDto) {
        if (!(userDto.getName().isEmpty() || userDto.getPassword().isEmpty())) {
            dbServiceUser.saveUser(userDto.toUser());
        }
        return new RedirectView("/admin", true);
    }
}
