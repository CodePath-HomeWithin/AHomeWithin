package org.ahomewithin.ahomewithin.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by barbara on 3/5/16.
 */
@Parcel
public class Person {
    public int id;
    public String title;
    public String firstName;
    public String lastName;
    public ArrayList<String> credentials;
    public String profile;
    public String pictureUrl;

    public Person() {
        credentials = new ArrayList<String>();
    }

    public static Person fromJSON(JSONObject jsonObject) {
        Person person = new Person();
        try {
            person.title = jsonObject.getString("title");
            person.firstName = jsonObject.getString("firstName");
            person.lastName = jsonObject.getString("lastName");
            person.profile = jsonObject.getString("profile");
            JSONArray jsonCreds = jsonObject.getJSONArray("credentials");
            if (jsonCreds != null) {
                for(int i = 0; i < jsonCreds.length()-1; i++) {
                    person.credentials.add(jsonCreds.get(i).toString());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return person;
    }
}
