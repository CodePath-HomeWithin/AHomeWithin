package org.ahomewithin.ahomewithin.util;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

/**
 * Created by barbara on 3/5/16.
 * src:  http://stackoverflow.com/questions/3574644/how-can-i-find-the-latitude-and-longitude-from-address/27834110#27834110
 * src:  https://github.com/codepath/android_guides/issues/142
 *
 * http://javapapers.com/android/android-get-address-with-street-name-city-for-location-with-geocoding/
 */
public class GeoCoder {

    // This needs to be run inside an AsyncTask!

    // getLocationFromAddress(this, "171 Lopez Ave, Menlo Park, CA");
    public LatLng getLocationFromAddress(Context context,String strAddress) {
        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;
        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) { return null; }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return p1;
    }

    // This needs to be run inside an AsyncTask!

    public String getAddressFromLatLng(Context context, float lat, float lng) {
        Geocoder geocoder;
        List<Address> addresses;
        geocoder = new Geocoder(context, Locale.getDefault());
        try {
            addresses = geocoder.getFromLocation(lat, lng, 1);
            String address = addresses.get(0).getAddressLine(0);
            String city = addresses.get(0).getAddressLine(1);
            String country = addresses.get(0).getAddressLine(2);
            return address + "\n" + city + ", " + country;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
