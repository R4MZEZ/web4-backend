package servlets;

import java.io.IOException;
import java.security.Key;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.LoginAnswer;
import myejb.ControllerEJB;
import myejb.SecretKeyEJB;
import org.json.JSONException;
import org.json.JSONObject;

@WebServlet("/fakeLogin")
public class FakeLoginServlet extends HttpServlet {

  @EJB
  private SecretKeyEJB secretKeyEJB;
  @EJB
  private ControllerEJB ejb;

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String jsonData = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    JSONObject receivedData = new JSONObject(jsonData);

    String moderDetails = receivedData.getString("moderator");
    String username = receivedData.getString("username");

    Key secretKey = secretKeyEJB.getSecretKey();

    try {
      receivedData = new JSONObject(moderDetails);
    } catch (JSONException e) {
      secretKey = null;
    }

    resp.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");

    try {
      if (secretKeyEJB.checkToken(
          secretKey,
          receivedData.getString("jwt"),
          receivedData.getString("username"))) {

        int role = ejb.getRole(receivedData.getInt("id"));
        System.out.println("role: " + role);
        System.out.println("username: " + receivedData.getString("username"));
        if (role > 1) {
          resp.getWriter().write(new JSONObject(new LoginAnswer(username)).toString());
        }else {
          resp.getWriter().write(new JSONObject(
              new LoginAnswer(3))
              .toString());
        }
      } else {
        resp.getWriter().write(new JSONObject(
            new LoginAnswer(3))
            .toString());
      }
    } catch (NullPointerException e) {
      resp.getWriter().write(new JSONObject(
          new LoginAnswer(4))
          .toString());
    }

    resp.getWriter().close();
  }


}
