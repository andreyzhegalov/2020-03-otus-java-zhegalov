package hw12.core.service;

import hw12.core.model.User;

import java.util.Optional;
import java.util.List;

public interface DBServiceUser {

    long saveUser(User user);

    Optional<User> getUser(long id);

    Optional<User> getUserByName(String name);

    List<User> getAllUsers();
}
