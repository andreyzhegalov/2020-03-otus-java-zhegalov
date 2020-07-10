package hw14.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import hw14.services.InitializerService;

@Controller
public class LoginController {
    public LoginController(InitializerService initializerService) {
        initializerService.prepareUsers();
    }

    @GetMapping("/")
    public String loginPageView() {
        return "login.html";
    }

    @PostMapping("/login")
    public RedirectView handleLogin(@RequestParam Map<String, String> requestParams) {
        final var login = requestParams.get("login");
        final var password = requestParams.get("password");
        return new RedirectView("/admin", true);
    }
}

