package hw11.service;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hw11.cachehw.HwCache;
import hw11.cachehw.MyCache;
import hw11.core.dao.UserDao;
import hw11.core.model.AdressDataSet;
import hw11.core.model.PhoneDataSet;
import hw11.core.model.User;
import hw11.core.service.DBServiceUser;
import hw11.core.service.DbServiceUserImpl;
import hw11.hibernate.HibernateUtils;
import hw11.hibernate.dao.UserDaoHibernate;
import hw11.hibernate.sessionmanager.SessionManagerHibernate;

public class DbServiceUserImplTest {
    private UserDao userDao;

    @BeforeEach
    public void setUp() {
        SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class,
                AdressDataSet.class, PhoneDataSet.class);

        SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        userDao = new UserDaoHibernate(sessionManager);
    }

    @Test
    public void testGetUserWithOutCache() {
        final DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao, null);

        final List<Long> idList = new ArrayList<>();
        for (final var user : getUsers()) {
            final long id = dbServiceUser.saveUser(user);
            idList.add(id);
        }

        long startTime = System.currentTimeMillis();
        for (Long id : idList) {
            dbServiceUser.getUser(id);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Duration geting users with out cache " + (endTime - startTime) + " milliseconds");
    }

    @Test
    public void testGetUserWithCache() {
        final HwCache<String, User> cache = new MyCache<>();
        final DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao, cache);

        final List<Long> idList = new ArrayList<>();
        for (final var user : getUsers()) {
            final long id = dbServiceUser.saveUser(user);
            idList.add(id);
        }

        long startTime = System.currentTimeMillis();
        for (Long id : idList) {
            dbServiceUser.getUser(id);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("Duration geting users with cache " + (endTime - startTime) + " milliseconds");
    }

    private List<User> getUsers(){
        final List<User> userList = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            final var user = new User("Вася");
            user.setAdress(new AdressDataSet("some street"));
            user.addPhone(new PhoneDataSet("111111"));
            userList.add(user);
        }
        return userList;
    }
}
