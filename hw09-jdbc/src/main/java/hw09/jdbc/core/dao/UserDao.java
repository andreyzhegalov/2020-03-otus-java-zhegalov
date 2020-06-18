package hw09.jdbc.core.dao;

import java.util.Optional;

import hw09.jdbc.core.model.User;
import hw09.jdbc.core.sessionmanager.SessionManager;

public interface UserDao {
    Optional<User> findById(long id);

    long insertUser(User user);

    void updateUser(User user);

    void insertOrUpdate(User user);

    SessionManager getSessionManager();
}
