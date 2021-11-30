package dao;

import java.util.List;
import model.Point;
import model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.query.Query;
import utils.HibernateFactory;

public class UserDAO {


  public int add(String username, String password) {
    User user = new User(username, password, getMaxId(), 1);
    Session session = HibernateFactory.getSessionFactory().openSession();
    Transaction tx1 = session.beginTransaction();
    Query query = session.createQuery("from model.User where username = :username");
    query.setParameter("username", user.getUsername());
    if (query.list().isEmpty()) {
      session.save(user);
      tx1.commit();
      session.close();
      return user.getId();
    }else {
      tx1.commit();
      session.close();
      return -1;
    }
  }

  public int checkUser(String username, String password) {

    Session session = HibernateFactory.getSessionFactory().openSession();

    Query<User> query = session.createQuery(
        "from User where username = :username and password = :password");
    query.setParameter("username", username);
    query.setParameter("password", password);

    if (query.list().isEmpty())
      return -1;
    else
      return query.list().stream().findFirst().get().getId();

  }

  public int getMaxId() {
    Session session = HibernateFactory.getSessionFactory().openSession();

    DetachedCriteria maxId = DetachedCriteria.forClass(User.class)
        .setProjection(Projections.max("id"));

    User maxUser = (User) session.createCriteria(User.class)
        .add(Property.forName("id").eq(maxId))
        .uniqueResult();

    return maxUser == null ? 0 : maxUser.getId();
  }

  public User getUser(String username){
    Session session = HibernateFactory.getSessionFactory().openSession();

    Query query = session.createQuery("from User where username = :username");
    query.setParameter("username", username);

    return (User)query.getSingleResult();
  }


  public int getRole(int user_id) {
    Session session = HibernateFactory.getSessionFactory().openSession();

    Query query = session.createQuery("from User where id = :user_id");
    query.setParameter("user_id", user_id);

    return ((User)query.getSingleResult()).getRole();
  }

  public List<User> findAll() throws NullPointerException {
    Session session = HibernateFactory.getSessionFactory().openSession();

    Query query = session.createQuery("from User");

    return (List<User>) query.list();
  }
}

