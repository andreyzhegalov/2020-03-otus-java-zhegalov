package hw16.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.view.RedirectView;

import hw16.core.service.DBServiceUser;
import hw16.dto.UserDto;

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
        return new RedirectView("/users", true);
    }

    @GetMapping({ "/users" })
    public String userListView(Model model) {
        // final var users = dbServiceUser.getAllUsers();
        // model.addAttribute("users", users);
        return "users.html";
    }
}
