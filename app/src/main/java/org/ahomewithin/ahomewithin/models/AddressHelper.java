package org.ahomewithin.ahomewithin.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

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
                for(int i = 0; i < streetLines.length()-1; i++) {
                    address.setAddressLine(i, streetLines.get(i).toString());
                }
            }
            address.setCountryCode("US");
            address.setPostalCode(jsonObject.getString("zipcode"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return address;
    }
}
