package hw10.hibernate.dao;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityGraph;

import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hw10.core.dao.UserDao;
import hw10.core.dao.UserDaoException;
import hw10.core.model.User;
import hw10.core.sessionmanager.SessionManager;
import hw10.hibernate.sessionmanager.DatabaseSessionHibernate;
import hw10.hibernate.sessionmanager.SessionManagerHibernate;

public class UserDaoHibernate implements UserDao {
    private static final Logger logger = LoggerFactory.getLogger(UserDaoHibernate.class);

    private final SessionManagerHibernate sessionManager;

    public UserDaoHibernate(SessionManagerHibernate sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Optional<User> findById(long id) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            EntityGraph<User> graph = (EntityGraph<User>) currentSession.getHibernateSession().getEntityGraph("graph.userEntity.addresesAndPhones");
            Map<String, Object> properties = new HashMap<>();
            properties.put("javax.persistence.loadgraph", graph);

            return Optional.ofNullable(currentSession.getHibernateSession().find(User.class, id, properties));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return Optional.empty();
    }

    @Override
    public long insertUser(User user) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            hibernateSession.persist(user);
            hibernateSession.flush();
            return user.getId();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public void updateUser(User user) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            hibernateSession.merge(user);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public void insertOrUpdate(User user) {
        DatabaseSessionHibernate currentSession = sessionManager.getCurrentSession();
        try {
            Session hibernateSession = currentSession.getHibernateSession();
            if (user.getId() > 0) {
                hibernateSession.merge(user);
            } else {
                hibernateSession.persist(user);
                hibernateSession.flush();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            throw new UserDaoException(e);
        }
    }

    @Override
    public SessionManager getSessionManager() {
        return sessionManager;
    }
}
