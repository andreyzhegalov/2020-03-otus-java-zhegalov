package hw09.jdbc.jdbc.mapper;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.math.BigDecimal;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hw09.jdbc.h2.DataSourceH2;
import hw09.jdbc.jdbc.DbExecutorImpl;
import hw09.jdbc.jdbc.mapper.testingclasses.Account;
import hw09.jdbc.jdbc.sessionmanager.SessionManagerJdbc;

public class JdbcMapperImplTestForAccount {

    private SessionManagerJdbc sessionManager;
    private DbExecutorImpl<Account> dbExecutor;
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
        final JdbcMapper<Account> daoJdbc = new JdbcMapperImpl<>(dbExecutor, sessionManager, Account.class);
        final var user = new Account(0, "CD", BigDecimal(30));
        daoJdbc.insert(user);
        assertThat(user.getNo()).isGreaterThan(0);
    }

    @Test
    public void testUpdate() {
        final JdbcMapper<Account> daoJdbc = new JdbcMapperImpl<>(dbExecutor, sessionManager, Account.class);
        final var user = new Account(0, "CD", BigDecimal(30));
        daoJdbc.insert(user);
        final long newId = user.getNo();

        user.setRest(BigDecimal(35));
        System.out.println(user);
        assertDoesNotThrow(() -> daoJdbc.update(user));

        final Account newUser = daoJdbc.findById(newId);
        assertEquals(newId, newUser.getNo());
    }

    @Test
    public void testInsertOrUpdate() {
        final JdbcMapper<Account> daoJdbc = new JdbcMapperImpl<>(dbExecutor, sessionManager, Account.class);
        final long initId = 0;
        final var account = new Account(initId, "CD", BigDecimal(30));
        daoJdbc.insertOrUpdate(account);
        assertNotEquals(initId, account.getNo());
        account.setRest(BigDecimal(35));
        final long updatedId = account.getNo();
        daoJdbc.insertOrUpdate(account);
        final var updatedUser = (Account) daoJdbc.findById(updatedId);
        assertEquals(BigDecimal(35), updatedUser.getRest());
    }

    private BigDecimal BigDecimal(int i) {
        return null;
    }

    @Test
    public void testFindByIdFailed() throws SQLException {
        final JdbcMapper<Account> daoJdbc = new JdbcMapperImpl<>(dbExecutor, sessionManager, Account.class);
        final Account newUser = daoJdbc.findById(0);
        assertThat(newUser).isNull();
    }

    @Test
    public void testFindById() throws SQLException {
        final JdbcMapper<Account> daoJdbc = new JdbcMapperImpl<>(dbExecutor, sessionManager, Account.class);
        final var account = new Account(0, "CD", BigDecimal(30));
        daoJdbc.insert(account);

        final Account newUser = daoJdbc.findById(account.getNo());
        assertThat(newUser).isNotNull();
        assertThat(newUser.getType()).isEqualTo("CD");
        assertThat(newUser.getRest()).isEqualTo(BigDecimal(30));
    }

    private void createTable(DataSource dataSource) throws SQLException {
        try (var connection = dataSource.getConnection();
                var pst = connection.prepareStatement(
                        "create table account(no bigint(20) auto_increment, type varchar(255), rest number)")) {
            pst.executeUpdate();
        }
        System.out.println("account table created");
    }

    private void deleteTable(DataSource dataSource) throws SQLException {
        try (var connection = dataSource.getConnection(); var pst = connection.prepareStatement("drop table account")) {
            pst.executeUpdate();
        }
        System.out.println("account table deleted");
    }
}
