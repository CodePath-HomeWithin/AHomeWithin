package org.ahomewithin.ahomewithin.models;

import android.content.DialogInterface;
import android.text.TextUtils;

import com.parse.ParseGeoPoint;

import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.parseModel.ParseItem;
import org.ahomewithin.ahomewithin.parseModel.ParseObjectUser;
import org.ahomewithin.ahomewithin.util.DateHelper;
import org.json.JSONArray;
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
    public enum UserType {
        SERVICE_PROVIDER, COMMUNITY
    }

    public UserType type;
    public String title;
    public String firstName;
    public String lastName;
    public String description;
    public String email;
    public String phone;
    public List<String> credentials;
    public List<ParseItem> purchasedItems;
    public String profileUrl;

    public double lat;
    public double lng;

    private static User currentUser;
    public static String PWD_HOLDER = "1sdf234efdssdfsd";



    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(ParseObjectUser parseObjectUser) {
        currentUser = getNewInstanceFromParseObject(parseObjectUser);
    }

    public User() {
        this.credentials = new ArrayList<String>();
        this.purchasedItems = new ArrayList<>();
    }

    public User(String name, String email, String phone, String description, User.UserType userType) {
        super();
        this.title = null;
        this.firstName = null;
        this.lastName = name;
        this.email = email;
        this.phone = phone;
        this.description = description;
        this.type = userType;
        this.profileUrl = "null";
        this.lat = 0;
        this.lng = 0;
    }

    public User(
            String name, String email, String description, User.UserType type,
            String phone, List<ParseItem> purchasedItems, String profileUrl,
            double lat, double lng
    ) {
        super();
        this.title = null;
        this.firstName = null;
        this.lastName = name;
        this.email = email;
        this.description = description;
        this.type = type;
        this.phone = phone;
        this.purchasedItems = purchasedItems;
        this.profileUrl = profileUrl;
        this.lat = lat;
        this.lng = lng;
    }

    public interface OnCreateUserListener extends Serializable {
        void onCreateUserListener(DialogInterface dialog, User user, String password);
    }

    public static User getNewInstanceFromParseObject(ParseObjectUser poUser) {
        ParseGeoPoint location = poUser.getGeo();
        double lon = location == null ? 0 : location.getLongitude();
        double lan = location == null ? 0 : location.getLatitude();
        User newUser = new User(
                poUser.getName(), poUser.getEmail(), poUser.getDesp(), UserType.SERVICE_PROVIDER,
                poUser.getPhone(), poUser.getItems(), poUser.getProfile(),
                lan, lon);
        return newUser;
    }

    public static ArrayList<User> fromJSONArray(JSONArray jsonArray) {
        ArrayList<User> users = new ArrayList<User>();
        for(int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject itemJson = jsonArray.getJSONObject(i);
                User item = User.fromJSON(itemJson);
                if (item != null) {
                    users.add(item);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }
        return users;
    }

    public static User fromJSON(JSONObject jsonObject) {
        User user = new User();
        try {
            user.lat = jsonObject.getDouble("lat");
            user.lng = jsonObject.getDouble("lng");
            if (!jsonObject.isNull("title")) {
                user.title = jsonObject.getString("title");
            }
            if (!jsonObject.isNull("firstName")) {
                user.firstName = jsonObject.getString("firstName");
            }
            if (!jsonObject.isNull("lastName")) {
                user.lastName = jsonObject.getString("lastName");
            }
            if (!jsonObject.isNull("email")) {
                user.email = jsonObject.getString("email");
            }
            if (!jsonObject.isNull("description")) {
                user.description = jsonObject.getString("description");
            }
            if (!jsonObject.isNull("phone")) {
                user.phone = jsonObject.getString("phone");
            }
            if (!jsonObject.isNull("type")) {
                switch(jsonObject.getString("type")) {
                    case"service_provider":
                        user.type = UserType.SERVICE_PROVIDER;
                        break;
                    default:
                        user.type = UserType.COMMUNITY;
                }
            }
            if (user.type == null) {
                user.type = UserType.COMMUNITY;
            };
            if (!jsonObject.isNull("credentials")) {
                JSONArray jsonCreds = jsonObject.getJSONArray("credentials");
                if (jsonCreds != null) {
                    for (int i = 0; i < jsonCreds.length(); i++) {
                        user.credentials.add(jsonCreds.get(i).toString());
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }


    public String getFullName(boolean withTokens) {
        List<String> tokens = new ArrayList<String>();
        if (title != null) {
            tokens.add(this.title);
        }
        if (firstName != null) {
            tokens.add(firstName);
        }
        if (lastName != null) {
            tokens.add(lastName);
        }
        if (withTokens && (credentials != null)) {
            tokens.add(TextUtils.join(", ", credentials));
        }
        return(TextUtils.join(" ", tokens));
    }



//    public static User fromJSON(JSONObject jsonObject) {
//        User user = new User();
//        try {
//            user.lat = jsonObject.getDouble("lat");
//            user.lon = jsonObject.getDouble("lon");
//
////            if (!jsonObject.isNull("type")) {
////                user.type = jsonObject.getString("type");
////            }
//
//            if (!jsonObject.isNull("name")) {
//                user.name = jsonObject.getString("name");
//            }
//
//            if (!jsonObject.isNull("desp")) {
//                user.description = jsonObject.getString("desp");
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        return user;
//    }
}
