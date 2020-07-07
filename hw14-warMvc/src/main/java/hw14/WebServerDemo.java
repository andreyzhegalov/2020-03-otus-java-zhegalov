package hw14;

import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import hw14.config.AppConfig;
import hw14.core.dao.UserDao;
import hw14.core.model.AdressDataSet;
import hw14.core.model.PhoneDataSet;
import hw14.core.model.User;
import hw14.core.service.DbServiceUserImpl;
import hw14.hibernate.HibernateUtils;
import hw14.hibernate.dao.UserDaoHibernate;
import hw14.hibernate.sessionmanager.SessionManagerHibernate;
import hw14.server.UsersWebServer;
import hw14.services.InitializerService;
import hw14.services.TemplateProcessor;
import hw14.services.TemplateProcessorImpl;
import hw14.services.UserAuthService;
import hw14.services.UserAuthServiceImpl;

public class WebServerDemo {
    private static final String TEMPLATES_DIR = "/templates/";
    private static final String HIBERNATE_CONF = "hibernate.cfg.xml";

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            System.out.println("Server port not defined. Setup port argument");
            return;
        }

        final ApplicationContext ctx = new AnnotationConfigApplicationContext(AppConfig.class);

        final SessionFactory sessionFactory = HibernateUtils.buildSessionFactory(HIBERNATE_CONF, User.class,
                AdressDataSet.class, PhoneDataSet.class);
        final SessionManagerHibernate sessionManager = ctx.getBean(SessionManagerHibernate.class);

        final UserDao userDao = new UserDaoHibernate(sessionManager);
        final var dbServiceUser = new DbServiceUserImpl(userDao, null);

        final var initializerService = new InitializerService(dbServiceUser);
        initializerService.prepareUsers();

        final UserAuthService authService = new UserAuthServiceImpl(dbServiceUser);
        final TemplateProcessor templateProcessor = new TemplateProcessorImpl(TEMPLATES_DIR);

        final UsersWebServer usersWebServer = new UsersWebServer(Integer.parseInt(args[0]), templateProcessor,
                authService, dbServiceUser);

        usersWebServer.start();
        usersWebServer.join();
    }

}
