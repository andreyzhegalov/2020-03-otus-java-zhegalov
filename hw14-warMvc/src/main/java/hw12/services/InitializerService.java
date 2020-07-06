package hw12.services;

import org.hibernate.SessionFactory;

import hw12.core.dao.UserDao;
import hw12.core.model.AdressDataSet;
import hw12.core.model.PhoneDataSet;
import hw12.core.model.User;
import hw12.core.service.DBServiceUser;
import hw12.core.service.DbServiceUserImpl;
import hw12.hibernate.HibernateUtils;
import hw12.hibernate.dao.UserDaoHibernate;
import hw12.hibernate.sessionmanager.SessionManagerHibernate;

public class InitializerService {
    private static final String HIBERNATE_CONF = "hibernate.cfg.xml";
    private final DBServiceUser dbServiceUser;

    public InitializerService() {
        final SessionFactory sessionFactory = HibernateUtils.buildSessionFactory(HIBERNATE_CONF, User.class,
                AdressDataSet.class, PhoneDataSet.class);
        final SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        final UserDao userDao = new UserDaoHibernate(sessionManager);
        this.dbServiceUser = new DbServiceUserImpl(userDao, null);
        prepareUsers();
    }

    public DBServiceUser getDbUserService() {
        return dbServiceUser;
    }

    private void prepareUsers() {
        final var admin = new User("admin");
        admin.setPassword("11111");
        dbServiceUser.saveUser(admin);
        final var user1 = new User("user1");
        user1.setPassword("22222");
        dbServiceUser.saveUser(user1);
    }
}
