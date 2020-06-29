package hw12.core.dao;

import hw12.core.model.User;
import hw12.core.sessionmanager.SessionManager;

import java.util.Optional;
import java.util.List;

public interface UserDao {
    Optional<User> findById(long id);

    Optional<User> findByName(String name);

    List<User> getAllUsers();

    long insertUser(User user);

    void updateUser(User user);

    void insertOrUpdate(User user);

    SessionManager getSessionManager();
}
