package org.ahomewithin.ahomewithin.util;

import android.content.Context;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.ahomewithin.ahomewithin.AHomeWithinClient;
import org.ahomewithin.ahomewithin.models.User;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by barbara on 3/12/16.
 */
public class MapMarkers {

    ArrayList<User> users;
    
    public MapMarkers(Context context) {
        users = new ArrayList<User>();
        try {
            JSONObject response = AHomeWithinClient.getUsers(context);
            users = User.fromJSONArray(response.getJSONArray("users"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void addMarkersToMap(GoogleMap map) {
        if (users != null) {
            for(User user: users) {
                switch(user.type) {
                    case SERVICE_PROVIDER:
                        markMap(map, user.getLat(), user.getLng(), user.getFullName(), "provider", BitmapDescriptorFactory.HUE_RED);
                        break;
                    default: // COMMUNITY:
                        markMap(map, user.getLat(), user.getLng(), user.getFullName(), "", BitmapDescriptorFactory.HUE_AZURE);
                }
            }
        }
    }

    private void markMap(GoogleMap map, double lat, double lng, String title, String snippet, float color) {
        try {
            map.addMarker(new MarkerOptions()
                    .position(new LatLng(lat, lng))
                    .title(title)
                    .snippet(snippet)
                    .icon(BitmapDescriptorFactory.defaultMarker(color)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}
