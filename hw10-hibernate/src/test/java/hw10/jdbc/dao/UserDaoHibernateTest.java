package hw10.jdbc.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.hibernate.SessionFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hw10.core.model.AdressDataSet;
import hw10.core.model.PhoneDataSet;
import hw10.core.model.User;
import hw10.hibernate.HibernateUtils;
import hw10.hibernate.dao.UserDaoHibernate;
import hw10.hibernate.sessionmanager.SessionManagerHibernate;

class UserDaoHibernateTest {

    private static final String HIBERNATE_CFG_XML_FILE_RESOURCE = "hibernate-test.cfg.xml";
    private SessionFactory sessionFactory;
    private SessionManagerHibernate sessionManagerHibernate;
    private UserDaoHibernate userDaoHibernate;

    @BeforeEach
    public void setUp() {
        sessionFactory = HibernateUtils.buildSessionFactory(HIBERNATE_CFG_XML_FILE_RESOURCE, User.class,
                AdressDataSet.class, PhoneDataSet.class);
        sessionManagerHibernate = new SessionManagerHibernate(sessionFactory);
        userDaoHibernate = new UserDaoHibernate(sessionManagerHibernate);
    }

    @AfterEach
    void tearDown() {
        sessionFactory.close();
    }

    // protected EntityStatistics getUserStatistics() {
    // Statistics stats = sessionFactory.getStatistics();
    // return stats.getEntityStatistics(User.class.getName());
    // }

    @Test
    void shouldCorrectFindUserById() {
        User expectedUser = new User("Name");
        expectedUser.setAdress(new AdressDataSet("some street"));
        expectedUser.addPhone(new PhoneDataSet("12345"));
        expectedUser.addPhone(new PhoneDataSet("54321"));

        saveUserDao(expectedUser);
        assertThat(expectedUser.getId()).isGreaterThan(0);

        sessionManagerHibernate.beginSession();
        Optional<User> mayBeUser = userDaoHibernate.findById(expectedUser.getId());
        sessionManagerHibernate.commitSession();
        assertThat(mayBeUser.get()).isEqualTo(expectedUser);
    }

    @Test
    void shouldCorrectSaveNewUser() {
        User newUser = new User("Name");
        newUser.setAdress(new AdressDataSet("some street"));
        newUser.addPhone(new PhoneDataSet("12345"));
        newUser.addPhone(new PhoneDataSet("54321"));

        sessionManagerHibernate.beginSession();
        userDaoHibernate.insertOrUpdate(newUser);
        long newUserId = newUser.getId();
        sessionManagerHibernate.commitSession();

        assertThat(newUserId).isGreaterThan(0);

        final Optional<User> actualUser = findUserDao(newUserId);
        assertThat(actualUser.isPresent()).isTrue();
        assertThat(actualUser).get().isEqualTo(newUser);
        System.out.println(actualUser.get().getAdress().getStreet());
    }

    @Test
    void shouldCorrectSaveExistUser() {
        final User existedUser = new User("Name");
        existedUser.setAdress(new AdressDataSet("some street"));
        existedUser.addPhone(new PhoneDataSet("12345"));
        existedUser.addPhone(new PhoneDataSet("54321"));

        saveUserDao(existedUser);
        final long existedUserId = existedUser.getId();
        assertThat(existedUserId).isGreaterThan(0);

        final Optional<User> mayBeModifiedUser = findUserDao(existedUserId);
        assertThat(mayBeModifiedUser.isPresent()).isTrue();
        final User modifiedUser = mayBeModifiedUser.get();
        modifiedUser.setName("New Name");
        modifiedUser.getAdress().setStreet("new street");
        modifiedUser.removePhone(modifiedUser.getPhones().get(0));
        modifiedUser.getPhones().get(0).setNumber("11111");
        saveUserDao(modifiedUser);

        final Optional<User> mayBeUpdatedUser = findUserDao(mayBeModifiedUser.get().getId());
        assertThat(mayBeUpdatedUser.isPresent()).isTrue();
        final User updatedUser = mayBeUpdatedUser.get();
        assertThat(existedUser.getId()).isEqualTo(updatedUser.getId());

        assertThat(modifiedUser).isEqualTo(updatedUser);
    }

    private void saveUserDao(User user) {
        sessionManagerHibernate.beginSession();
        userDaoHibernate.insertOrUpdate(user);
        sessionManagerHibernate.commitSession();
    }

    private Optional<User> findUserDao(long id) {
        sessionManagerHibernate.beginSession();
        Optional<User> mayBeUser = userDaoHibernate.findById(id);
        sessionManagerHibernate.commitSession();
        return mayBeUser;
    }

    @Test
    void getSessionManager() {
        assertThat(userDaoHibernate.getSessionManager()).isNotNull().isEqualTo(sessionManagerHibernate);
    }

}
