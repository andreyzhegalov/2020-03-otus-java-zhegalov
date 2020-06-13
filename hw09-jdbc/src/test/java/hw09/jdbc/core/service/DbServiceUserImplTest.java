package hw09.jdbc.core.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.sql.SQLException;

import javax.sql.DataSource;

import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hw09.jdbc.core.model.User;
import hw09.jdbc.h2.DataSourceH2;
import hw09.jdbc.jdbc.DbExecutorImpl;
import hw09.jdbc.jdbc.dao.UserDaoMapperJdbc;
import hw09.jdbc.jdbc.sessionmanager.SessionManagerJdbc;

public class DbServiceUserImplTest {
    private SessionManagerJdbc sessionManager;
    private DataSourceH2 dataSource;
    private DbExecutorImpl<User> dbExecutor;
    private UserDaoMapperJdbc userDao;

    @BeforeEach
    private void setUp() throws SQLException {
        dataSource = new DataSourceH2();
        createTable(dataSource);
        sessionManager = new SessionManagerJdbc(dataSource);
        dbExecutor = new DbExecutorImpl<>();
        userDao = new UserDaoMapperJdbc(sessionManager, dbExecutor);
    }

    @AfterEach
    private void tearDown() throws SQLException {
        deleteTable(dataSource);
    }

    @Test
    public void testSaveUser() {
        var dbServiceUser = new DbServiceUserImpl(userDao);
        assertTrue(dbServiceUser.saveUser(new User(0, "dbServiceUser")) > 0);
    }

    @Test
    public void testGetUser() {
        var dbServiceUser = new DbServiceUserImpl(userDao);
        var id = dbServiceUser.saveUser(new User(0, "dbServiceUser"));
        Optional<User> user = dbServiceUser.getUser(id);
        assertNotNull(user.get());
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
