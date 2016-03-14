package org.ahomewithin.ahomewithin.models;

import android.content.DialogInterface;

import com.parse.ParseGeoPoint;

import org.ahomewithin.ahomewithin.parseModel.ParseItem;
import org.ahomewithin.ahomewithin.parseModel.ParseObjectUser;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by barbara on 3/5/16.
 */

//This is the mirror of ParseObjectUser + ParseUser
//as a model to communicate with other parts
@Parcel
public class User {

  public String name;
  public String email;
  public String desp;
  public String type;
  public String phone;
  public List<ParseItem> purchasedItems;
  public String profileUrl;

  public double lat;
  public double lon;

  private static User currentUser;
  public static String PWD_HOLDER = "1sdf234efdssdfsd";

  public User(String name, String email, String phone, String desp, String userType) {
    this.name = name;
    this.email = email;
    this.phone = phone;
    this.desp = desp;
    this.type = userType;
    this.purchasedItems = new ArrayList<>();
    this.profileUrl = "null";
    this.lat = 0;
    this.lon = 0;
  }

  public static User getCurrentUser() {
    return currentUser;
  }

  public static void setCurrentUser(ParseObjectUser parseObjectUser) {
    currentUser = getNewInstanceFromParseObject(parseObjectUser);
  }

  public User() {}

  public User(
      String name, String email, String desp, String type,
      String phone, List<ParseItem> purchasedItems, String profileUrl,
      double lat, double lon
  ) {
    this.name = name;
    this.email = email;
    this.desp = desp;
    this.type = type;
    this.phone = phone;
    this.purchasedItems = purchasedItems;
    this.profileUrl = profileUrl;
    this.lat = lat;
    this.lon = lon;
  }

  public interface OnCreateUserListener extends Serializable {
    void onCreateUserListener(DialogInterface dialog, User user, String password);
  }

  public static User getNewInstanceFromParseObject(ParseObjectUser poUser) {
    ParseGeoPoint location = poUser.getGeo();
    double lon = location == null? 0 : location.getLongitude();
    double lan = location == null? 0 : location.getLatitude();
    User newUser = new User(
        poUser.getName(), poUser.getEmail(), poUser.getDesp(), poUser.getType(),
        poUser.getPhone(), poUser.getItems(), poUser.getProfile(),
        lan, lon);
    return newUser;
  }

  public static User fromJSON(JSONObject jsonObject) {
    User user = new User();
    try {
      user.lat = jsonObject.getDouble("lat");
      user.lon = jsonObject.getDouble("lon");

      if(!jsonObject.isNull("type")) {
        user.type = jsonObject.getString("type");
      }

      if(!jsonObject.isNull("name")) {
        user.name = jsonObject.getString("name");
      }

      if(!jsonObject.isNull("desp")) {
        user.desp = jsonObject.getString("desp");
      }

    } catch (JSONException e) {
      e.printStackTrace();
    }
    return user;
  }

}
