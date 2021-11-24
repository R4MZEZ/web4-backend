package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "POINTS")
public class Point {

  private int id;
  private double x;
  private double y;
  private double r;
  private boolean hit;
  private String time;
  private long script_time;
  private static int nextID = 1;
  private int user_id;

  public Point(double x, double y, double r, long startTime, User user) {
    this.id = nextID++;
    this.x = x;
    this.y = y;
    this.r = r;
    this.user_id = user.getId();
    hit = calculate(x, y, r);
    time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    script_time = System.currentTimeMillis() - startTime;

  }

  public Point() {
  }

  @Id
  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public double getX() {
    return x;
  }

  public void setX(double x) {
    this.x = x;
  }

  public double getY() {
    return y;
  }

  public void setY(double y) {
    this.y = y;
  }

  public void setR(double r) {
    this.r = r;
  }

  public double getR() {
    return r;
  }

  public void setTime(String time) {
    this.time = time;
  }

  public String getTime() {
    return time;
  }

  public void setScript_time(long script_time) {
    this.script_time = script_time;
  }

  public long getScript_time() {
    return script_time;
  }

  public boolean getHit() {
    return hit;
  }

  public void setHit(boolean hit) {
    this.hit = hit;
  }

  public boolean calculate(double x, double y, double r) {

    if (x >= 0 && y <= 0) {
      return y >= x - r/2;
    }
    if (x <= 0 && y <= 0) {
      return y >= -r/2 && x >= -r;
    }
    if (x <= 0 && y >= 0) {
      return x*x + y*y <= r*r/4;
    }
    return false;
  }

  public int getUser_id() {
    return user_id;
  }

  public void setUser_id(int user_id) {
    this.user_id = user_id;
  }
}