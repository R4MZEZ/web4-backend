import myejb.SecretKeyEJB;
import java.io.IOException;
import java.security.Key;
import java.util.stream.Collectors;
import javax.ejb.EJB;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.LoginAnswer;
import org.json.JSONException;
import org.json.JSONObject;


@WebServlet("/checkPoint")
public class ControllerServlet extends HttpServlet {

  @EJB
  private DemoEJB demoEJB;
  @EJB
  private SecretKeyEJB secretKeyEJB;


  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String jsonData = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    JSONObject receivedData = new JSONObject(jsonData);

    float x = receivedData.getFloat("x");
    float y = receivedData.getFloat("y");
    float r = receivedData.getFloat("r");
    String token = receivedData.getString("token");

    Key secretKey = secretKeyEJB.getSecretKey();

    try {
      receivedData = new JSONObject(token);
    } catch (JSONException e) {
      secretKey = null;
    }

    resp.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");

    if (secretKeyEJB.checkToken(
        secretKey,
        receivedData.getString("jwt"),
        receivedData.getString("username"))) {

      resp.getWriter().write(new JSONObject(
          demoEJB.checkPoint(x, y, r, receivedData.getString("username")))
          .toString());
    } else {
      resp.getWriter().write(new JSONObject(
          new LoginAnswer(3))
          .toString());
    }

    resp.getWriter().close();
  }

}
