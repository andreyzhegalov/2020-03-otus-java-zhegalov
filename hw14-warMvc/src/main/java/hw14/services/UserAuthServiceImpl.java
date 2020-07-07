package hw14.services;

import hw14.core.service.DBServiceUser;

public class UserAuthServiceImpl implements UserAuthService {
    private final DBServiceUser dbUserService;

    public UserAuthServiceImpl(DBServiceUser dbUserService) {
        this.dbUserService = dbUserService;
    }

    @Override
    public boolean authenticate(String login, String password) {
        return dbUserService.getUserByName(login).map(user -> user.getPassword().equals(password)).orElse(false);
    }

}
