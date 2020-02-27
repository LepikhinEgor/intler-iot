package intler_iot.config;

import intler_iot.controllers.entities.OrderData;
import intler_iot.dao.entities.*;
import intler_iot.dao.hibernate.UserDaoHibernate;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.*;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
@EnableAspectJAutoProxy
@Import({ SecurityConfig.class })
@ComponentScan(basePackages = {"intler_iot.controllers", "intler_iot.services", "intler_iot.dao"})
public class SpringConfig {

    @Bean
    SessionFactory getSessionFactory() {
        org.hibernate.cfg.Configuration configuration = new org.hibernate.cfg.Configuration().configure();
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Device.class);
        configuration.addAnnotatedClass(Sensor.class);
        configuration.addAnnotatedClass(CloudOrder.class);
        configuration.addAnnotatedClass(Widget.class);
        configuration.addAnnotatedClass(ControlCommand.class);
        configuration.addAnnotatedClass(CommandCondition.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        SessionFactory sessionFactory = configuration.buildSessionFactory(builder.build());

        return sessionFactory;
    }
}
