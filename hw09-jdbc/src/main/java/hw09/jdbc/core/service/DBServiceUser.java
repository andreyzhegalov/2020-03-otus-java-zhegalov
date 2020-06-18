package hw09.jdbc.core.service;

import hw09.jdbc.core.model.User;

import java.util.Optional;

public interface DBServiceUser {

    long saveUser(User user);

    Optional<User> getUser(long id);
}
