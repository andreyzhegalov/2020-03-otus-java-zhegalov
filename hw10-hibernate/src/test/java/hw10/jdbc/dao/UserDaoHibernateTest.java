package hw10.jdbc.dao;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import hw10.AbstractHibernateTest;
import hw10.core.model.AdressDataSet;
import hw10.core.model.PhoneDataSet;
import hw10.core.model.User;
import hw10.hibernate.dao.UserDaoHibernate;
import hw10.hibernate.sessionmanager.SessionManagerHibernate;

@DisplayName("Dao для работы с пользователями должно ")
class UserDaoHibernateTest extends AbstractHibernateTest {

    private SessionManagerHibernate sessionManagerHibernate;
    private UserDaoHibernate userDaoHibernate;

    @BeforeEach
    @Override
    public void setUp() {
        super.setUp();
        sessionManagerHibernate = new SessionManagerHibernate(sessionFactory);
        userDaoHibernate = new UserDaoHibernate(sessionManagerHibernate);
    }

    @Test
    @DisplayName(" корректно загружать пользователя по заданному id")
    void shouldFindCorrectUserById() {
        User expectedUser = new User("Вася");
        AdressDataSet userAdress = new AdressDataSet();
        userAdress.setStreet("some street");
        expectedUser.setAdress(userAdress);
        expectedUser.addPhone(new PhoneDataSet("12345"));
        expectedUser.addPhone(new PhoneDataSet("54321"));

        saveUser(expectedUser);
        assertThat(expectedUser.getId()).isGreaterThan(0);

        sessionManagerHibernate.beginSession();
        Optional<User> mayBeUser = userDaoHibernate.findById(expectedUser.getId());
        sessionManagerHibernate.commitSession();

        assertThat(mayBeUser.get().getPhones()).isNotNull();
        // assertThat(mayBeUser.get().getPhones()).isInstanceOf(HibernateProxy.class);
        assertThat(mayBeUser).isPresent().get().isEqualToComparingFieldByField(expectedUser);
    }

    @DisplayName(" корректно сохранять пользователя")
    @Test
    void shouldCorrectSaveUser() {
        User expectedUser = new User("Вася");
        AdressDataSet userAdress = new AdressDataSet();
        userAdress.setStreet("some street");
        expectedUser.setAdress(userAdress);
        expectedUser.addPhone(new PhoneDataSet("12345"));
        expectedUser.addPhone(new PhoneDataSet("54321"));

        sessionManagerHibernate.beginSession();
        userDaoHibernate.insertOrUpdate(expectedUser);
        long id = expectedUser.getId();
        sessionManagerHibernate.commitSession();

        assertThat(id).isGreaterThan(0);

        // User actualUser = loadUser(id);
        // assertThat(actualUser).isNotNull().hasFieldOrPropertyWithValue("name", expectedUser.getName());
        //
        // expectedUser = new User(id, "Не Вася");
        // sessionManagerHibernate.beginSession();
        // userDaoHibernate.insertOrUpdate(expectedUser);
        // long newId = expectedUser.getId();
        // sessionManagerHibernate.commitSession();
        //
        // assertThat(newId).isGreaterThan(0).isEqualTo(id);
        // actualUser = loadUser(newId);
        // assertThat(actualUser).isNotNull().hasFieldOrPropertyWithValue("name", expectedUser.getName());
    }

    @DisplayName(" возвращать менеджер сессий")
    @Test
    void getSessionManager() {
        assertThat(userDaoHibernate.getSessionManager()).isNotNull().isEqualTo(sessionManagerHibernate);
    }

}
