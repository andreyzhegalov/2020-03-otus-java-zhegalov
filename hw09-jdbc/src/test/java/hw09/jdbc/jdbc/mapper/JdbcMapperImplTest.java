package hw09.jdbc.jdbc.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hw09.jdbc.h2.DataSourceH2;
import hw09.jdbc.jdbc.DbExecutorImpl;
import hw09.jdbc.jdbc.mapper.testingclasses.User;
import hw09.jdbc.jdbc.sessionmanager.SessionManagerJdbc;

public class JdbcMapperImplTest {
    private SessionManagerJdbc sessionManager;
    private DbExecutorImpl<User> dbExecutor;
    private DataSourceH2 dataSource;

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
    public void testInsert() throws SQLException {
        final JdbcMapper<User> daoJdbc = new JdbcMapperImpl<>(dbExecutor, sessionManager, User.class);
        final var user = new User(0, "Name", 30);
        daoJdbc.insert(user);
        assertThat(user.getUserId()).isGreaterThan(0);
    }

    @Test
    public void testUpdate() {
        final JdbcMapper<User> daoJdbc = new JdbcMapperImpl<>(dbExecutor, sessionManager, User.class);
        final var user = new User(0, "Name", 30);
        daoJdbc.insert(user);
        final long newId = user.getUserId();

        final int newAge = 35;
        user.setUserAge(newAge);
        assertDoesNotThrow(() -> daoJdbc.update(user));

        final User newUser = daoJdbc.findById(newId);
        assertEquals(newId, newUser.getUserId());
        assertEquals(newAge, newUser.getUserAge());
    }

    @Test
    public void testInsertOrUpdate() {
        final JdbcMapper<User> daoJdbc = new JdbcMapperImpl<>(dbExecutor, sessionManager, User.class);
        final long initId = 0;
        final var user = new User(initId, "Name", 30);
        daoJdbc.insertOrUpdate(user);
        assertNotEquals(initId, user.getUserId());
        final long newId  = user.getUserId();
        final int newAge = 35;
        user.setUserAge(newAge);
        daoJdbc.insertOrUpdate(user);
        final User updatedUser = daoJdbc.findById(newId);
        assertEquals(newAge, updatedUser.getUserAge());
    }

    @Test
    public void testFindByIdFailed() throws SQLException {
        final JdbcMapper<User> daoJdbc = new JdbcMapperImpl<>(dbExecutor, sessionManager, User.class);
        final User newUser = daoJdbc.findById(0);
        assertThat(newUser).isNull();
    }

    @Test
    public void testFindById() throws SQLException {
        final JdbcMapper<User> daoJdbc = new JdbcMapperImpl<>(dbExecutor, sessionManager, User.class);
        final var user = new User(0, "Name", 30);
        daoJdbc.insert(user);
        final User newUser = daoJdbc.findById(user.getUserId());
        assertThat(newUser).isNotNull();
        assertThat(newUser.getUserName()).isEqualTo("Name");
        assertThat(newUser.getUserAge()).isEqualTo(30);
    }

    private void createTable(DataSource dataSource) throws SQLException {
        try (var connection = dataSource.getConnection();
                var pst = connection.prepareStatement(
                        "create table user(user_id bigint(20) auto_increment, user_name varchar(255), user_age int(3))")) {
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
