package hw12.core.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hw12.cachehw.HwCache;
import hw12.cachehw.HwCacheException;
import hw12.core.dao.UserDao;
import hw12.core.model.User;
import hw12.core.sessionmanager.SessionManager;

public class DbServiceUserImpl implements DBServiceUser {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceUserImpl.class);

    private final UserDao userDao;
    private final HwCache<String, User> cache;

    public DbServiceUserImpl(UserDao userDao, HwCache<String, User> cache) {
        this.userDao = userDao;
        this.cache = cache;
    }

    @Override
    public long saveUser(User user) {
        long userId;
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
            cache.put(String.valueOf(userId), user);
        }
        return userId;
    }

    @Override
    public Optional<User> getUser(long id) {
        if (cache != null) {
            try {
                final var user = cache.get(String.valueOf(id));
                return Optional.of(user);
            } catch (HwCacheException ignored) {
            }
        }

        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                Optional<User> userOptional = userDao.findById(id);
                if ((cache != null) && (userOptional.isPresent())) {
                    cache.put(String.valueOf(id), userOptional.get());
                }

                logger.info("user: {}", userOptional.orElse(null));
                return userOptional;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
            return Optional.empty();
        }
    }

    public List<User> getAllUsers() {
        try (SessionManager sessionManager = userDao.getSessionManager()) {
            sessionManager.beginSession();
            try {
                final var users = userDao.getAllUsers();
                if (cache != null) {
                    for (final var user : users) {
                        cache.put(String.valueOf(user.getId()), user);
                    }
                }
                return users;
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
                sessionManager.rollbackSession();
            }
        }
        return new ArrayList<>();
    }
}
