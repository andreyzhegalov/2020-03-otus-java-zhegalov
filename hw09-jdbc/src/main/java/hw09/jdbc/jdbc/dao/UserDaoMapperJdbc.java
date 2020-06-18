package hw09.jdbc.jdbc.dao;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hw09.jdbc.core.dao.UserDao;
import hw09.jdbc.core.dao.UserDaoException;
import hw09.jdbc.core.model.User;
import hw09.jdbc.core.sessionmanager.SessionManager;
import hw09.jdbc.jdbc.mapper.JdbcMapper;
import hw09.jdbc.jdbc.sessionmanager.SessionManagerJdbc;

public class UserDaoMapperJdbc implements UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoMapperJdbc.class);
    private final JdbcMapper<User> jdbcMapper;
    private final SessionManagerJdbc sessionManager;

    public UserDaoMapperJdbc(SessionManagerJdbc sessionManager, JdbcMapper<User> jdbcMapper) {
        this.sessionManager = sessionManager;
        this.jdbcMapper = jdbcMapper;
    }

    @Override
    public Optional<User> findById(long id) {
        try {
            return Optional.of(jdbcMapper.findById(id));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public long insertUser(User user) {
        try {
            jdbcMapper.insertOrUpdate(user);
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

    @Override
    public void updateUser(User user) {
        try {
            jdbcMapper.update(user);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public void insertOrUpdate(User user) {
        try {
            jdbcMapper.insertOrUpdate(user);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }
}
