package hw09.jdbc.jdbc.dao;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import hw09.jdbc.core.model.User;
import hw09.jdbc.h2.DataSourceH2;
import hw09.jdbc.jdbc.sessionmanager.SessionManagerJdbc;

public class UserDaoMapperJdbcTest {
    private SessionManagerJdbc sessionManager;
    private DataSourceH2 dataSource;

    @BeforeEach
    private void setUp() throws SQLException {
        dataSource = new DataSourceH2();
        createTable(dataSource);
        sessionManager = new SessionManagerJdbc(dataSource);
        sessionManager.beginSession();
    }

    @AfterEach
    private void tearDown() throws SQLException {
        sessionManager.close();
        deleteTable(dataSource);
    }

    @Test
    public void testFindById(){
        assertThrows(UnsupportedOperationException.class, () -> new UserDaoMapperJdbc().findById(0));
    }

    @Test
    public void testInsertUser(){
        assertThrows(UnsupportedOperationException.class, ()->new UserDaoMapperJdbc().insertUser(new User()));
    }

    @Test
    public void testGetSessionManager(){
        assertThrows(UnsupportedOperationException.class, ()->new UserDaoMapperJdbc().getSessionManager());
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

