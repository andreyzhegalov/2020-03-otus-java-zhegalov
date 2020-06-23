package hw11.core.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hw11.cachehw.HwCache;
import hw11.cachehw.HwCacheExeption;
import hw11.core.dao.UserDao;
import hw11.core.model.User;
import hw11.core.sessionmanager.SessionManager;

import java.util.Optional;

public class DbServiceUserImpl implements DBServiceUser {
    private static Logger logger = LoggerFactory.getLogger(DbServiceUserImpl.class);

    private final UserDao userDao;
    private final HwCache<Long, User> cache;

    public DbServiceUserImpl(UserDao userDao, HwCache<Long, User> cache) {
        this.userDao = userDao;
        this.cache = cache;
    }

    @Override
    public long saveUser(User user) {
        long userId = -1;
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                userDao.insertOrUpdate(user);
                userId = user.getId();
                sessionManager.commitSession();

                logger.info("created user: {}", userId);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
                throw new DbServiceException(e);
            }
        }
        if (cache != null) {
            cache.put(userId, user);
        }
        return userId;
    }

    @Override
    public Optional<User> getUser(long id) {
        if (cache != null) {
            try {
                final var user = cache.get(id);
                return Optional.of(user);
            } catch (HwCacheExeption e) {
            }
        }

        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<User> userOptional = userDao.findById(id);

                logger.info("user: {}", userOptional.orElse(null));
                return userOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }
}
