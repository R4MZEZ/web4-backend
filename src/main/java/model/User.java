package model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity(name = "User")
@Table(name = "USERS")
public class User {

  @Id
  private int id;
  private String username;
  private String password;
  private int role; //1-user, 2-moder, 3-admin
//  @OneToMany(mappedBy = "user")
//  private List<Point> points;

  public User(String username, String password, int id, int role) {
    this.id = ++id;
    this.username = username;
    this.password = password;
    this.role = role;
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

  public int getRole() {
    return role;
  }

  public void setRole(int role) {
    this.role = role;
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", username='" + username + '\'' +
        ", password='" + password + '\'' +
        '}';
  }
}
