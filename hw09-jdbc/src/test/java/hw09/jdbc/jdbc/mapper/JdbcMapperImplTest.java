package hw09.jdbc.jdbc.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
        assertThat(user.getId()).isGreaterThan(0);
    }

    @Test
    public void testUpdate() {
        final JdbcMapper<User> daoJdbc = new JdbcMapperImpl<>(dbExecutor, sessionManager, User.class);
        final var user = new User(0, "Name", 30);
        daoJdbc.insert(user);
        final long newId = user.getId();

        user.setAge(35);
        System.out.println(user);
        assertDoesNotThrow(()-> daoJdbc.update(user));

        final User newUser = daoJdbc.findById(newId, User.class);
        assertEquals(newId, newUser.getId());
    }

    @Test
    public void testInsertOrUpdate() {
        assertThrows(UnsupportedOperationException.class, () -> new JdbcMapperImpl<User>(null, null, User.class).insertOrUpdate(null));
    }

    @Test
    public void testFindByIdFailed() throws SQLException {
        final JdbcMapper<User> daoJdbc = new JdbcMapperImpl<>(dbExecutor, sessionManager, User.class);
        final User newUser = daoJdbc.findById(0, User.class);
        assertThat(newUser).isNull();
    }

    @Test
    public void testFindById() throws SQLException {
        final JdbcMapper<User> daoJdbc = new JdbcMapperImpl<>(dbExecutor, sessionManager, User.class);
        final var user = new User(0, "Name", 30);
        daoJdbc.insert(user);

        final User newUser = daoJdbc.findById(user.getId(), User.class);
        assertThat(newUser).isNotNull();
        assertThat(newUser.getName()).isEqualTo("Name");
        assertThat(newUser.getAge()).isEqualTo(30);
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
