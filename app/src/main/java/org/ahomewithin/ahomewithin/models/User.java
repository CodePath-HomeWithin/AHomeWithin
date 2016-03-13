package org.ahomewithin.ahomewithin.models;

import android.content.DialogInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by barbara on 3/5/16.
 */
@Parcel
public class User {
    public int id;
    public String title;
    public String firstName;
    public String lastName;
    public ArrayList<String> credentials;
    public String description;

    public long lat;
    public long lon;

    public ArrayList<Item> purchasedItems;
    // TODO prepared for usage when needed
    //public ArrayList<Chat> conversations;

    public String profile;
    public String profileUrl;

    public String email;
    public String phone;

    //Name, Email and phone of a user
    //password is not stored; instead
    //it's maintained as credential in Firebase
    //Also, this is just a template, so there
    //may be more fields coming


    public User() {
        credentials = new ArrayList<String>();
    }

    public User(String name, String email, String phone) {
        this.firstName = name;
        this.email = email;
        this.phone = phone;
    }

    public static User fromJSON(JSONObject jsonObject) {
        User user = new User();
        try {
            user.title = jsonObject.getString("title");
            user.firstName = jsonObject.getString("firstName");
            user.lastName = jsonObject.getString("lastName");
            user.profile = jsonObject.getString("profile");
            JSONArray jsonCreds = jsonObject.getJSONArray("credentials");
            if (jsonCreds != null) {
                for(int i = 0; i < jsonCreds.length()-1; i++) {
                    user.credentials.add(jsonCreds.get(i).toString());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return user;
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


    /**
     * Created by xiangyang_xiao on 3/6/16.
     */
    public static interface OnCreateUserListener extends Serializable {
      void onCreateUserListener(DialogInterface dialog, User user, String password);
    }
}
