package org.ahomewithin.ahomewithin.parseModel;

import com.parse.ParseClassName;
import com.parse.ParseGeoPoint;
import com.parse.ParseObject;

import org.ahomewithin.ahomewithin.models.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiangyang_xiao on 3/12/16.
 */

@ParseClassName("User")
public class ParseObjectUser extends ParseObject {

  public static final String PARSE_NAME = "User";

  //email used as user name to guarantee uniqueness
  //String email;
  public static final String EMAIL_KEY = "email";

  //String name;
  public static final String NAME_KEY = "name";

  //String desp;
  public static final String DESP_KEY = "desp";

  //in tradition, database should use _ instead of camel case
  //DOCTOR, PARENT
  //Not user enum, as enum can't inherit from ParseObject
  //String userType;
  public static final String TYPE_KEY = "user_type";

  //No validation yet in database
  //expects the validation to happen at the UI
  //String phoneNum;
  public static final String PHONE_KEY = "phone_num";

  //GEO
  //ParseGeoPoint location;
  public static final String GEO_KEY = "location";

  //Products purchased
  //List<ParseItem> items;
  public static final String ITEMS_KEY = "items";

  //profile url, optional
  //String profile_url;
  public static final String PROFILE_KEY = "profile_url";

  public String getEmail() {
    return getString(EMAIL_KEY);
  }

  public String getName() {
    return getString(NAME_KEY);
  }

  public String getDesp() {
    return getString(DESP_KEY);
  }

  public User.UserType getType() {
    if("Doctor".equals(getString(TYPE_KEY))) {
      return User.UserType.SERVICE_PROVIDER;
    } else {
      return User.UserType.COMMUNITY;
    }
  }

  public String getPhone() {
    return getString(PHONE_KEY);
  }

  public ParseGeoPoint getGeo() {
    return getParseGeoPoint(GEO_KEY);
  }

  public List<ParseItem> getItems() {
    return getList(ITEMS_KEY);
  }

  public String getProfile() {
    return getString(PROFILE_KEY);
  }

  public void setEmail(String email) {
    put(EMAIL_KEY, email);
  }

  public void setName(String name) {
    put(NAME_KEY, name);
  }

  public void setDesp(String desp) {
    put(DESP_KEY, desp);
  }

  public void setType(User.UserType userType) {
    switch (userType) {
      case SERVICE_PROVIDER: put (TYPE_KEY, "Doctor"); break;
      case COMMUNITY: put (TYPE_KEY, "Parent"); break;
    }
  }

  public void setPhone(String phoneNum) {
    put(PHONE_KEY, phoneNum);
  }

  public void setGeo(ParseGeoPoint location) {
    put(GEO_KEY, location);
  }

  public void setItems(List<ParseItem> items) {
    put(ITEMS_KEY, items);
  }

  public void setProfile(String profileUrl) {
    put(PROFILE_KEY, profileUrl);
  }

  public void updateUserBasicInfo(User user) {
    setEmail(user.email);
    setName(user.name);
    setDesp(user.description);
    setType(user.type);
    setPhone(user.phone);
  }

  public void updateUser(User user) {
    setEmail(user.email);
    setName(user.name);
    setDesp(user.description);
    setType(user.type);
    setPhone(user.phone);
    setGeo(new ParseGeoPoint(user.lat, user.lon));
    setItems(new ArrayList<ParseItem>());
    setProfile(user.profileUrl);
  }
}
