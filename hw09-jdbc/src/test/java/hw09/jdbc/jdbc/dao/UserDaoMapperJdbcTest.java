package hw09.jdbc.jdbc.dao;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hw09.jdbc.core.dao.UserDaoException;
import hw09.jdbc.core.model.User;
import hw09.jdbc.h2.DataSourceH2;
import hw09.jdbc.jdbc.DbExecutorImpl;
import hw09.jdbc.jdbc.sessionmanager.SessionManagerJdbc;

public class UserDaoMapperJdbcTest {
    private SessionManagerJdbc sessionManager;
    private DataSourceH2 dataSource;
    private DbExecutorImpl<User> dbExecutor;

    @BeforeEach
    private void setUp() throws SQLException {
        dataSource = new DataSourceH2();
        createTable(dataSource);
        sessionManager = new SessionManagerJdbc(dataSource);
        dbExecutor = new DbExecutorImpl<>();
        sessionManager.beginSession();
    }

    @AfterEach
    private void tearDown() throws SQLException {
        sessionManager.close();
        deleteTable(dataSource);
    }

    @Test
    public void testCtr() {
        assertDoesNotThrow(() -> new UserDaoMapperJdbc(sessionManager, dbExecutor));
    }

    @Test
    public void testInsertUser() {
        final var userDao = new UserDaoMapperJdbc(sessionManager, dbExecutor);
        final var user = new User(0, "UserName", 25);
        userDao.insertUser(user);
        assertNotEquals(0, user.getId());
    }

    @Test
    public void testFindByIdFialed() {
        final var userDao = new UserDaoMapperJdbc(sessionManager, dbExecutor);
        assertFalse(userDao.findById(0).isPresent());
    }

    @Test
    public void testFindById() {
        final var userDao = new UserDaoMapperJdbc(sessionManager, dbExecutor);
        final User user = new User(0, "TestUser", 20);
        final long id = userDao.insertUser(user);
        assertTrue(userDao.findById(id).isPresent());
    }

    @Test
    public void testUpdateUser() {
        final var userDao = new UserDaoMapperJdbc(sessionManager, dbExecutor);
        final User user = new User(0, "TestUser", 20);
        userDao.insertUser(user);
        final int newAge = 35;
        user.setAge(newAge);
        assertDoesNotThrow(() -> userDao.updateUser(user));
        final var updatedUser = userDao.findById(user.getId());
        assertTrue(updatedUser.isPresent());
        assertEquals(newAge, updatedUser.get().getAge());
    }

    @Test
    public void testUpdateUserFailed() {
        final var userDao = new UserDaoMapperJdbc(sessionManager, dbExecutor);
        final User user = new User(0, "TestUser", 20);
        assertThrows(UserDaoException.class, () -> userDao.updateUser(user));
    }

    @Test
    public void testInsertOrUpdate() {
        final var userDao = new UserDaoMapperJdbc(sessionManager, dbExecutor);
        final User user = new User(0, "TestUser", 20);
        assertDoesNotThrow(() -> userDao.insertOrUpdate(user));
        assertTrue(user.getId() > 0);
    }

    @Test
    public void testGetSessionManager() {
        final var userDao = new UserDaoMapperJdbc(sessionManager, dbExecutor);
        assertNotNull(userDao.getSessionManager());
    }

    private void createTable(DataSource dataSource) throws SQLException {
        try (var connection = dataSource.getConnection();
                var pst = connection.prepareStatement(
                        "create table user(id bigint(20) auto_increment, name varchar(255), age int(3))")) {
            pst.executeUpdate();
        }
        System.out.println("user table created");
    }

    private void deleteTable(DataSource dataSource) throws SQLException {
        try (var connection = dataSource.getConnection(); var pst = connection.prepareStatement("drop table user")) {
            pst.executeUpdate();
        }
        System.out.println("user table deleted");
    }

}
