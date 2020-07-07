package hw14.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import hw14.core.dao.UserDao;
import hw14.core.model.AdressDataSet;
import hw14.core.model.PhoneDataSet;
import hw14.core.model.User;
import hw14.core.sessionmanager.SessionManager;
import hw14.hibernate.HibernateUtils;
import hw14.hibernate.dao.UserDaoHibernate;
import hw14.hibernate.sessionmanager.SessionManagerHibernate;

@Configuration
public class AppConfig {
    private static final String HIBERNATE_CONF = "hibernate.cfg.xml";

    @Bean
    public SessionManager sessionManagerHibernate(SessionFactory sessionFactory){
        return new SessionManagerHibernate(sessionFactory);
    }

    @Bean
    public SessionFactory sessionFactory(){
        return HibernateUtils.buildSessionFactory(HIBERNATE_CONF, User.class,
                AdressDataSet.class, PhoneDataSet.class);
    }

    @Bean
    public UserDao userDao(SessionManagerHibernate sessionManager){
        return new UserDaoHibernate(sessionManager);
    }

    // @Bean
    // public GameProcessor gameProcessor(IOService ioService,
    //                                    PlayerService playerService,
    //                                    EquationPreparer equationPreparer) {
    //     return new GameProcessorImpl(ioService, equationPreparer, playerService);
    // }
    //
    // @Bean
    // public IOService ioService() {
    //     return new IOServiceConsole(System.out, System.in);
    // }

}
