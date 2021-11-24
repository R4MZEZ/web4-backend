package dao;

import model.Point;
import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.query.Query;
import utils.HibernateFactory;

public class PointDAO {


  public void add(Point point) throws NullPointerException{
    point.setId(getMaxId()+1);
    Session session = HibernateFactory.getSessionFactory().openSession();
    Transaction tx1 = session.beginTransaction();
    session.save(point);
    tx1.commit();
    session.close();
  }

  public void clear(int user_id) throws NullPointerException{
    Session session = HibernateFactory.getSessionFactory().openSession();
    Transaction tx1 = session.beginTransaction();
    Query query = session.createQuery("delete from Point where user_id = :user_id");
    query.setParameter("user_id", user_id);

    query.executeUpdate();
    tx1.commit();
    session.close();
  }

  public List<Point> findAll(int user_id) throws NullPointerException {
    Session session = HibernateFactory.getSessionFactory().openSession();

    Query query = session.createQuery("from Point where user_id = :user_id");
    query.setParameter("user_id", user_id);

    return (List<Point>) query.list();
  }

  public int getMaxId() {
    Session session = HibernateFactory.getSessionFactory().openSession();

    DetachedCriteria maxId = DetachedCriteria.forClass(Point.class)
        .setProjection(Projections.max("id"));

    Point maxPoint = (Point) session.createCriteria(Point.class)
        .add(Property.forName("id").eq(maxId))
        .uniqueResult();

    return maxPoint == null ? 0 : maxPoint.getId();
  }
}