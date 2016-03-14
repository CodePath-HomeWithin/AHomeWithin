package org.ahomewithin.ahomewithin.models;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by barbara on 3/5/16.
 */
@Parcel
public class AddressHelper {

    // http://stackoverflow.com/questions/22096011/what-does-each-androids-location-address-method-return
    public static android.location.Address addressFromJson(JSONObject jsonObject) {
        android.location.Address address = new android.location.Address(Locale.ENGLISH);
        try {
            JSONArray streetLines = jsonObject.getJSONArray("lines");
            if (streetLines != null) {
                for(int i = 0; i < streetLines.length(); i++) {
                    String line = streetLines.getString(i);
                    address.setAddressLine(i, line);
                }
            }
            address.setLocality(jsonObject.getString("city"));
            address.setCountryCode("US");
            address.setPostalCode(jsonObject.getString("zipcode"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return address;
    }

    public static String getLocation(android.location.Address address) {
        if (address != null) {
            List<String> tokens = new ArrayList<String>();
            for(int i = 0; i <= address.getMaxAddressLineIndex(); i++) {
                tokens.add(address.getAddressLine(i));
            }
            if (address.getLocality() != null) {
                tokens.add(address.getLocality());
            }
            return(TextUtils.join(", ", tokens));
        }
        return "";
    }
}
