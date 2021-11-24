import dao.PointDAO;
import dao.UserDAO;
import javax.ejb.Stateless;
import model.Point;

@Stateless
public class DemoEJB {
  PointDAO pointDAO = new PointDAO();
  UserDAO userDAO = new UserDAO();

  public Point checkPoint(Float x, Float y, Float r, String username) throws IllegalArgumentException{
    Point checkedPoint = new Point(x,y,r, System.currentTimeMillis(), userDAO.getUser(username));
    pointDAO.add(checkedPoint);
    return checkedPoint;
  }

  public void clearPoints(int user_id){
    pointDAO.clear(user_id);
  }
}