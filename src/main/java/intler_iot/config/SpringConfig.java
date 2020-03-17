package intler_iot.config;

import intler_iot.dao.entities.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
@EnableAspectJAutoProxy
@Import({ SecurityConfig.class })
@ComponentScan(basePackages = {"intler_iot.controllers", "intler_iot.services", "intler_iot.dao"})
public class SpringConfig {

    private static final Logger logger = LoggerFactory.getLogger(SpringConfig.class);

    @Bean
    SessionFactory getSessionFactory() {
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration().configure();
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Device.class);
        configuration.addAnnotatedClass(SensorValue.class);
        configuration.addAnnotatedClass(CloudOrder.class);
        configuration.addAnnotatedClass(Widget.class);
        configuration.addAnnotatedClass(ControlCommand.class);
        configuration.addAnnotatedClass(CommandCondition.class);

        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());

        return sessionFactory;
    }
}
