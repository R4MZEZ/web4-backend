package servlets;

import java.io.IOException;
import java.util.stream.Collectors;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.LoginAnswer;
import org.json.JSONObject;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {

  @Override
  protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
    String jsonData = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
    JSONObject receivedData = new JSONObject(jsonData);

    String username = receivedData.getString("username");
    String password = receivedData.getString("password");

    resp.addHeader("Access-Control-Allow-Origin", "http://localhost:4200");
    try {
      resp.getWriter().write(new JSONObject(new LoginAnswer(username, password, true)).toString());
    }catch (NullPointerException ex){
      resp.getWriter().write(new JSONObject(
          new LoginAnswer(4))
          .toString());
    }
    resp.getWriter().close();
  }


}
