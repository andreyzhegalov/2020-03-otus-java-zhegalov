package hw14.config;

import java.io.IOException;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hw14.core.dao.UserDao;
import hw14.core.model.AdressDataSet;
import hw14.core.model.PhoneDataSet;
import hw14.core.model.User;
import hw14.core.service.DBServiceUser;
import hw14.core.service.DbServiceUserImpl;
import hw14.core.sessionmanager.SessionManager;
import hw14.hibernate.HibernateUtils;
import hw14.hibernate.dao.UserDaoHibernate;
import hw14.hibernate.sessionmanager.SessionManagerHibernate;
import hw14.server.UsersWebServer;
import hw14.services.InitializerService;
import hw14.services.TemplateProcessor;
import hw14.services.TemplateProcessorImpl;

@Configuration
public class AppConfig {
    private static final String HIBERNATE_CONF = "hibernate.cfg.xml";
    private static final String TEMPLATES_DIR = "/templates/";
    private static final int SERVER_PORT = 8080;

    @Bean
    public SessionManager sessionManagerHibernate(SessionFactory sessionFactory) {
        return new SessionManagerHibernate(sessionFactory);
    }

    @Bean
    public SessionFactory sessionFactory() {
        return HibernateUtils.buildSessionFactory(HIBERNATE_CONF, User.class, AdressDataSet.class, PhoneDataSet.class);
    }

    @Bean
    public UserDao userDao(SessionManagerHibernate sessionManager) {
        return new UserDaoHibernate(sessionManager);
    }

    @Bean
    public DBServiceUser dbServiceUser(UserDao userDao) {
        return new DbServiceUserImpl(userDao, null);
    }

    @Bean
    public InitializerService initializerService(DBServiceUser dbServiceUser) {
        return new InitializerService(dbServiceUser);
    }

    @Bean
    public TemplateProcessor templateProcessor() throws IOException {
        return new TemplateProcessorImpl(TEMPLATES_DIR);
    }

    @Bean
    public UsersWebServer usersWebServer(TemplateProcessor templateProcessor, InitializerService initializerService,
            DBServiceUser dbUserService) {
        return new UsersWebServer(SERVER_PORT, templateProcessor, initializerService, dbUserService);
    }
}
