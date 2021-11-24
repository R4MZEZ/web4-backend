package servlets;

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

@WebServlet("/points")
public class PointsServlet extends HttpServlet {

  @EJB
  private ControllerEJB ejb;
  @EJB
  private SecretKeyEJB secretKeyEJB;

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {

    String jsonData = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    JSONObject receivedData = new JSONObject(jsonData);
    String token = receivedData.getString("token");

    Key secretKey = secretKeyEJB.getSecretKey();

    try {
      receivedData = new JSONObject(token);
    } catch (JSONException e) {
      secretKey = null;
    }

    resp.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
    try {
      if (secretKeyEJB.checkToken(
          secretKey,
          receivedData.getString("jwt"),
          receivedData.getString("username"))) {

        List<Point> points = ejb.findAll(receivedData.getInt("id"));
        resp.getWriter().write(new JSONArray(points).toString());

      } else {
        resp.getWriter().write(new JSONObject(
            new LoginAnswer(3))
            .toString());
      }
    } catch (NullPointerException ex) {
      resp.getWriter().write(new JSONObject(
          new LoginAnswer(4))
          .toString());
    }

  }
}
