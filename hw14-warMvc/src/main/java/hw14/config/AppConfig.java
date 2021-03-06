package hw14.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import hw14.core.model.AdressDataSet;
import hw14.core.model.PhoneDataSet;
import hw14.core.model.User;
import hw14.hibernate.HibernateUtils;

@ComponentScan("hw14")
@Configuration
public class AppConfig {
    private static final String HIBERNATE_CONF = "hibernate.cfg.xml";

    @Bean
    public SessionFactory sessionFactory() {
        return HibernateUtils.buildSessionFactory(HIBERNATE_CONF, User.class, AdressDataSet.class, PhoneDataSet.class);
    }
}
