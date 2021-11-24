package utils;
import model.Point;

import model.User;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateFactory {
  private static SessionFactory sessionFactory;

  private HibernateFactory() {}

  public static SessionFactory getSessionFactory() {
    if (sessionFactory == null) {
      try {
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(Point.class);
        configuration.addAnnotatedClass(User.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());

      } catch (Exception e) {
        System.out.println("Исключение!" + e);
      }
    }
    return sessionFactory;
  }
}