package model;

import dao.UserDAO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.Key;
import myejb.SecretKeyEJB;

public class LoginAnswer {

  private String jwt;
  private int errCode;
  private String username;
  private int id;

  public LoginAnswer(String username, String password, boolean register) throws NullPointerException{

    UserDAO userDAO = new UserDAO();

    SecretKeyEJB secretKeyEJB = new SecretKeyEJB();
    Key secretKey;
    try {
      secretKey = secretKeyEJB.getSecretKey();
    }catch (IOException e){
      secretKey = null;
    }
    int returnedId;


    if (register) {
      returnedId = userDAO.add(username, password);
      if (returnedId == -1) {
        errCode = 1;
      } else {
        this.id = returnedId;
        this.jwt = Jwts.builder().setSubject(username).signWith(secretKey).compact();
        this.username = username;
      }
    }else{
      returnedId = userDAO.checkUser(username, password);
      if (returnedId == -1) {
        errCode = 2;
      } else {
        this.id = returnedId;
        this.jwt = Jwts.builder().setSubject(username).signWith(secretKey).compact();
        this.username = username;
      }

    }

  }

  public LoginAnswer(int errCode){
    this.errCode = errCode;
  }


  public String getJwt() {
    return jwt;
  }

  public void setJwt(String jwt) {
    this.jwt = jwt;
  }

  public int getErrCode() {
    return errCode;
  }

  public void setErrCode(int errCode) {
    this.errCode = errCode;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

}
