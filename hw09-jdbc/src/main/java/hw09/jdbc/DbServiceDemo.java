package hw09.jdbc;

import java.sql.SQLException;
import java.util.Optional;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hw09.jdbc.core.model.User;
import hw09.jdbc.core.service.DbServiceUserImpl;
import hw09.jdbc.h2.DataSourceH2;
import hw09.jdbc.jdbc.DbExecutorImpl;
import hw09.jdbc.jdbc.dao.UserDaoJdbc;
import hw09.jdbc.jdbc.sessionmanager.SessionManagerJdbc;

/**
 * @author sergey created on 03.02.19.
 */
public class DbServiceDemo {
    private static final Logger logger = LoggerFactory.getLogger(DbServiceDemo.class);

    public static void main(String[] args) throws Exception {
        var dataSource = new DataSourceH2();
        var demo = new DbServiceDemo();

        demo.createTable(dataSource);

        var sessionManager = new SessionManagerJdbc(dataSource);
        DbExecutorImpl<User> dbExecutor = new DbExecutorImpl<>();
        var userDao = new UserDaoJdbc(sessionManager, dbExecutor);

        var dbServiceUser = new DbServiceUserImpl(userDao);
        var id = dbServiceUser.saveUser(new User(0, "dbServiceUser"));
        Optional<User> user = dbServiceUser.getUser(id);

        user.ifPresentOrElse(crUser -> logger.info("created user, name:{}", crUser.getName()),
                () -> logger.info("user was not created"));

    }

    private void createTable(DataSource dataSource) throws SQLException {
        try (var connection = dataSource.getConnection();
                var pst = connection.prepareStatement("create table user(id long auto_increment, name varchar(50))")) {
            pst.executeUpdate();
        }
        System.out.println("table created");
    }
}
