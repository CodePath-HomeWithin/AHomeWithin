package org.ahomewithin.ahomewithin.models;

import android.location.Address;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by barbara on 3/5/16.
 */
@Parcel
public class Provider {

    public Address address;
    public User user;

    // chapter:  name, zipcode (or city, state) (lat/lng)
    // providerId
    // address, lat/lng
    // company name, phone, email
    // user first_name, last_name, suffix, profile

    public Provider() {
    }

    public static Provider fromJSON(JSONObject jsonObject) {
        Provider provider = new Provider();
        try {
            provider.address = AddressHelper.addressFromJson(jsonObject.getJSONObject("address"));
            provider.user = User.fromJSON(jsonObject.getJSONObject("person"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return provider;
    }

    public static ArrayList<Provider> fromJSONArray(JSONArray jsonArray) {
        ArrayList<Provider> providers = new ArrayList<Provider>();
        for(int i = 0; i < jsonArray.length(); i++) {
            try {
                JSONObject providerJson = jsonArray.getJSONObject(i);
                Provider provider = Provider.fromJSON(providerJson);
                if (provider != null) {
                    providers.add(provider);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                continue;
            }
        }
        return providers;
    }
}
