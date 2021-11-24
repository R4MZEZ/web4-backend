package myejb;

import dao.PointDAO;
import dao.UserDAO;
import java.util.List;
import javax.ejb.Stateless;
import javax.validation.constraints.Null;
import model.Point;

@Stateless
public class ControllerEJB {

  PointDAO pointDAO = new PointDAO();
  UserDAO userDAO = new UserDAO();

  public Point checkPoint(Float x, Float y, Float r, String username)
      throws IllegalArgumentException {
    Point checkedPoint = new Point(x, y, r, System.currentTimeMillis(), userDAO.getUser(username));
    pointDAO.add(checkedPoint);
    return checkedPoint;
  }

  public void clearPoints(int user_id) throws NullPointerException {
    pointDAO.clear(user_id);
  }

  public List<Point> findAll(int user_id) {
    return pointDAO.findAll(user_id);
  }
}