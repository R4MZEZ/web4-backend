package model;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity(name = "User")
@Table(name = "USERS")
public class User {

  @Id
  private int id;
  private String username;
  private String password;
//  @OneToMany(mappedBy = "user")
//  private List<Point> points;

  public User(String username, String password, int id) {
    this.id = ++id;
    this.username = username;
    this.password = password;
  }

  public User() {

  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

//  public List<Point> getPoints() {
//    return points;
//  }
//
//  public void setPoints(List<Point> points) {
//    this.points = points;
//  }


  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", username='" + username + '\'' +
        ", password='" + password + '\'' +
        '}';
  }
}
