package myejb;

import dao.PointDAO;
import dao.UserDAO;
import java.util.List;
import javax.ejb.Stateless;
import javax.validation.constraints.Null;
import model.Point;
import model.User;

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

  public int getRole(int user_id){
    return userDAO.getRole(user_id);
  }

  public List<User> findAllUsers() {
    return userDAO.findAll();
  }
}