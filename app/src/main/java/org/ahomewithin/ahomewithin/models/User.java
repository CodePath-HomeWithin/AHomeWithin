package org.ahomewithin.ahomewithin.models;

import android.content.DialogInterface;
import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by barbara on 3/5/16.
 */
@Parcel
public class User {

    public enum UserType {
        SERVICE_PROVIDER, COMMUNITY
    }

    public UserType type;
    public int id;
    public String title;
    public String firstName;
    public String lastName;
    public String credentials;
    public String description;

    public double lat;
    public double lng;

    public ArrayList<Item> purchasedItems;
    // TODO prepared for usage when needed
    //public ArrayList<Conversation> conversations;

    public String profileUrl;

    public String email;
    public String phone;

    //Name, Email and phone of a user
    //password is not stored; instead
    //it's maintained as credential in Firebase
    //Also, this is just a template, so there
    //may be more fields coming


    public User() {
    }

    public User(String name, String email, String phone) {
        this.firstName = name;
        this.email = email;
        this.phone = phone;
    }

    public static User fromJSON(JSONObject jsonObject) {
        User user = new User();
        try {
            user.lat = jsonObject.getDouble("lat");
            user.lng = jsonObject.getDouble("lng");

            if(!jsonObject.isNull("type")) {
                user.type = mapToType(jsonObject.getString("type"));
            }

            if(!jsonObject.isNull("title")) {
                user.title = jsonObject.getString("title");
            }
            if(!jsonObject.isNull("firstName")) {
                user.firstName = jsonObject.getString("firstName");
            }
            if(!jsonObject.isNull("lastName")) {
                user.lastName = jsonObject.getString("lastName");
            }
            if(!jsonObject.isNull("credentials")) {
                user.credentials = jsonObject.getString("credentials");
            }

            if(!jsonObject.isNull("description")) {
                user.description = jsonObject.getString("description");
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
    }

    public static ArrayList<User> fromJSONArray(JSONArray jsonArray) {
        ArrayList<User> users = new ArrayList<User>();
        for(int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject userJson = jsonArray.getJSONObject(i);
                User user = User.fromJSON(userJson);
                if (user != null) {
                    users.add(user);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }
        return users;
    }

    public static UserType mapToType(String type) {
        UserType ut = UserType.COMMUNITY;
        switch(type) {
            case "service_provider":  ut = UserType.SERVICE_PROVIDER;
        }
        return ut;
    }

    public String getName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }


    public String getFullName() {
        List<String> tokens = new ArrayList<String>();
        if (title != null) {
            tokens.add(title);
        }
        if (firstName != null) {
            tokens.add(firstName);
        }
        if (lastName != null) {
            tokens.add(lastName);
        }
        if (credentials != null) {
            tokens.add(", " + credentials);
        }
        return(TextUtils.join(" ", tokens));
    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Created by xiangyang_xiao on 3/6/16.
     */
    public static interface OnCreateUserListener extends Serializable {
      void onCreateUserListener(DialogInterface dialog, User user, String password);
    }
}
