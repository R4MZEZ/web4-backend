package servlets;

import model.User;
import myejb.ControllerEJB;
import myejb.SecretKeyEJB;
import java.io.IOException;
import java.security.Key;
import java.util.List;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.LoginAnswer;
import model.Point;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@WebServlet("/getUsers")
public class GetUsersServlet extends HttpServlet {

  @EJB
  private ControllerEJB ejb;
  @EJB
  private SecretKeyEJB secretKeyEJB;

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws IOException {

    resp.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
    try {
      List<User> users = ejb.findAllUsers();
      resp.getWriter().write(new JSONArray(users).toString());
    } catch (NullPointerException ex) {
      resp.getWriter().write(new JSONObject(
          new LoginAnswer(4))
          .toString());
    }
  }
}
