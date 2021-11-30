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

@WebServlet("/checkRole")
public class CheckRoleServlet extends HttpServlet {


  @EJB
  SecretKeyEJB secretKeyEJB = new SecretKeyEJB();
  @EJB
  ControllerEJB ejb = new ControllerEJB();


  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String jsonData = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    JSONObject receivedData = new JSONObject(jsonData);

    resp.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");

    String token = receivedData.getString("token");

    Key secretKey = secretKeyEJB.getSecretKey();

    try {
      receivedData = new JSONObject(token);
    } catch (JSONException e) {
      secretKey = null;
    }

    try {

      if (secretKeyEJB.checkToken(
          secretKey,
          receivedData.getString("jwt"),
          receivedData.getString("username"))) {

        int role = ejb.getRole(receivedData.getInt("id"));
        resp.getWriter().write(new JSONObject("{\"role\":\"" + role + "\"}").toString());
      } else {
        resp.getWriter().write(new JSONObject(
            new LoginAnswer(3))
            .toString());
      }
    }catch (NullPointerException e){
      resp.getWriter().write(new JSONObject(
          new LoginAnswer(4))
          .toString());
    }

    resp.getWriter().close();
  }


}
