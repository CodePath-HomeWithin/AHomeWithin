package org.ahomewithin.ahomewithin.user;

import java.io.Serializable;

/**
 * Created by xiangyang_xiao on 3/5/16.
 */
public class User implements Serializable {

  public User() {

  }

  public User(String name, String email, String phone) {
    this.name = name;
    this.email = email;
    this.phone = phone;
  }

  public String getName() {
    return name;
  }

  public String getEmail() {
    return email;
  }

  public String getPhone() {
    return phone;
  }

  String name;
  String email;
  String phone;
}
