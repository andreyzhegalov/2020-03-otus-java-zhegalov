package hw11.core.service;

import hw11.core.model.User;

import java.util.Optional;

public interface DBServiceUser {

    long saveUser(User user);

    Optional<User> getUser(long id);

}
