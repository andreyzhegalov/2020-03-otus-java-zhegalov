package hw12;

import org.hibernate.SessionFactory;

import hw12.core.dao.UserDao;
import hw12.core.model.AdressDataSet;
import hw12.core.model.PhoneDataSet;
import hw12.core.model.User;
import hw12.core.service.DBServiceUser;
import hw12.core.service.DbServiceUserImpl;
import hw12.hibernate.HibernateUtils;
import hw12.hibernate.dao.UserDaoHibernate;
import hw12.hibernate.sessionmanager.SessionManagerHibernate;
import hw12.server.UsersWebServer;
import hw12.services.TemplateProcessor;
import hw12.services.TemplateProcessorImpl;
import hw12.services.UserAuthService;
import hw12.services.UserAuthServiceImpl;

public class WebServerDemo {
    private static final String TEMPLATES_DIR = "/templates/";

    private static DBServiceUser prepareDbUsersService() {
        final SessionFactory sessionFactory = HibernateUtils.buildSessionFactory("hibernate.cfg.xml", User.class,
                AdressDataSet.class, PhoneDataSet.class);
        final SessionManagerHibernate sessionManager = new SessionManagerHibernate(sessionFactory);
        final UserDao userDao = new UserDaoHibernate(sessionManager);
        final DBServiceUser dbServiceUser = new DbServiceUserImpl(userDao, null);

        final var admin = new User("admin");
        admin.setPassword("11111");
        dbServiceUser.saveUser(admin);
        final var user1 = new User("user1");
        user1.setPassword("22222");
        dbServiceUser.saveUser(user1);

        return dbServiceUser;
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Server port not defined. Setup port argument");
            return;
        }

        final var dbServiceUser = prepareDbUsersService();

        final UserAuthService authService = new UserAuthServiceImpl(dbServiceUser);
        final TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        final UsersWebServer usersWebServer = new UsersWebServer(Integer.valueOf(args[0]), templateProcessor,
                authService, dbServiceUser);

        usersWebServer.start();
        usersWebServer.join();

    }

}
