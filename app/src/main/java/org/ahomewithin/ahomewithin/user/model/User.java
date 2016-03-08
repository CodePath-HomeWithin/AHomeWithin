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

  //Name, Email and phone of a user
  //password is not stored; instead
  //it's maintained as credential in Firebase
  //Also, this is just a template, so there
  //may be more fields coming
  String name;
  String email;
  String phone;
}
