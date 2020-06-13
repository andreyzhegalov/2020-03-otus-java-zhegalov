package hw09.jdbc.jdbc.dao;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hw09.jdbc.core.dao.UserDao;
import hw09.jdbc.core.dao.UserDaoException;
import hw09.jdbc.core.model.User;
import hw09.jdbc.core.sessionmanager.SessionManager;
import hw09.jdbc.jdbc.DbExecutorImpl;
import hw09.jdbc.jdbc.mapper.JdbcMapperImpl;
import hw09.jdbc.jdbc.sessionmanager.SessionManagerJdbc;

public class UserDaoMapperJdbc implements UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoMapperJdbc.class);
    private final JdbcMapperImpl<User> jdbcMapperImpl;
    private final SessionManagerJdbc sessionManager;

    public UserDaoMapperJdbc(SessionManagerJdbc sessionManager, DbExecutorImpl<User> dbExecutor) {
        this.sessionManager = sessionManager;
        this.jdbcMapperImpl = new JdbcMapperImpl<>(dbExecutor, sessionManager, User.class);
    }

    @Override
    public Optional<User> findById(long id) {
        try {
            return Optional.of(jdbcMapperImpl.findById(id));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public long insertUser(User user) {
        try {
            jdbcMapperImpl.insertOrUpdate(user);
            return user.getId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return this.sessionManager;
    }
}
