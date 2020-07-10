package hw14.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import hw14.core.service.DBServiceUser;

@Controller
public class AdminController {
    private final DBServiceUser dbServiceUser;

    public AdminController(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @GetMapping({"/admin"})
    public String userListView(Model model) {
        final var users = dbServiceUser.getAllUsers();
        model.addAttribute("users", users);
        return "admin.html";
    }
}

