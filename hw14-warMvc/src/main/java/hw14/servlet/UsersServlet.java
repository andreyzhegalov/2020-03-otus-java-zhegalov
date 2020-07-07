package hw14.servlet;

import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import hw14.core.model.User;
import hw14.core.service.DBServiceUser;

public class UsersServlet extends HttpServlet {

    private static final String ADMIN_URI = "/admin";
    private static final String PARAM_NAME = "name";
    private static final String PARAM_PASSWORD = "password";

    private final DBServiceUser dbServiceUser;

    public UsersServlet(DBServiceUser dbServiceUser) {
        this.dbServiceUser = dbServiceUser;
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {

        final String name = request.getParameter(PARAM_NAME);
        final String password = request.getParameter(PARAM_PASSWORD);
        if (name.isEmpty() || password.isEmpty()) {
            response.sendRedirect(ADMIN_URI);
            return;
        }

        final var user = new User();
        user.setName(name);
        user.setPassword(password);
        dbServiceUser.saveUser(user);

        response.sendRedirect(ADMIN_URI);
    }

}
