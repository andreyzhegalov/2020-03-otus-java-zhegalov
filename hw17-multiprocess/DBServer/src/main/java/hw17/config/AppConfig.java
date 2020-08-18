package hw17.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

import hw17.cachehw.HwCache;
import hw17.cachehw.MyCache;
import hw17.core.model.AdressDataSet;
import hw17.core.model.PhoneDataSet;
import hw17.core.model.User;
import hw17.hibernate.HibernateUtils;

@Configuration
public class AppConfig implements WebSocketMessageBrokerConfigurer {
    private static final String HIBERNATE_CONF = "hibernate.cfg.xml";

    @Bean
    public SessionFactory sessionFactory() {
        return HibernateUtils.buildSessionFactory(HIBERNATE_CONF, User.class, AdressDataSet.class, PhoneDataSet.class);
    }

    @Bean
    public HwCache<String, User> hwCache() {
        return new MyCache<String, User>();
    }
}
