package hw09.jdbc.jdbc.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hw09.jdbc.h2.DataSourceH2;
import hw09.jdbc.jdbc.DbExecutorImpl;
import hw09.jdbc.jdbc.mapper.testingclasses.User;
import hw09.jdbc.jdbc.sessionmanager.SessionManagerJdbc;

public class DaoJdbcTest {
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
        final JdbcMapper<User> daoJdbc = new DaoJbdc<>(dbExecutor, sessionManager, User.class);
        final var userId = daoJdbc.insert(new User(0, "Name", 30));
        assertThat(userId).isGreaterThan(0);
    }

    @Test
    public void testUpate() {
        assertThrows(UnsupportedOperationException.class, () -> new DaoJbdc<>(null, null, null).update(null));
    }

    @Test
    public void testInsertOrUpdate() {
        assertThrows(UnsupportedOperationException.class, () -> new DaoJbdc<>(null, null, null).insertOrUpdate(null));
    }

    @Test
    public void testFindById() throws SQLException {
        final JdbcMapper<User> daoJdbc = new DaoJbdc<>(dbExecutor, sessionManager, User.class);
        final var userId = daoJdbc.insert(new User(0, "Name", 30));

        final User newUser = daoJdbc.findById(userId, User.class);
        assertThat(newUser).isNotNull();
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
        try (var connection = dataSource.getConnection();
                var pst = connection.prepareStatement(
                        "drop table user")) {
            pst.executeUpdate();
        }
        System.out.println("user table deleted");
    }
}
