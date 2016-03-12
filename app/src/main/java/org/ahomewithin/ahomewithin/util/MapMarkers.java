package org.ahomewithin.ahomewithin.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.ahomewithin.ahomewithin.AHomeWithinClient;
import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.models.User;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by barbara on 3/12/16.
 */
public class MapMarkers {

    class MapInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private View popup=null;
        private LayoutInflater inflater=null;

        MapInfoWindowAdapter(LayoutInflater inflater) {
            this.inflater=inflater;
        }

        @Override
        public View getInfoWindow(Marker marker) {
            return(null);
        }

        @SuppressLint("InflateParams")
        @Override
        public View getInfoContents(Marker marker) {
            if (popup == null) {
                popup=inflater.inflate(R.layout.map_info_window, null);
            }

            TextView tv=(TextView)popup.findViewById(R.id.title);

            tv.setText(marker.getTitle());
            tv=(TextView)popup.findViewById(R.id.snippet);
            tv.setText(marker.getSnippet());

            return(popup);
        }
    }

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


    public void addMarkersToMap(GoogleMap map, LayoutInflater inflater) {
        map.setInfoWindowAdapter(new MapInfoWindowAdapter(inflater));
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
