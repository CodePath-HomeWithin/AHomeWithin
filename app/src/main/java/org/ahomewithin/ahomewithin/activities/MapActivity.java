package org.ahomewithin.ahomewithin.activities;

/**
 * Created by barbara on 3/12/16.
 */

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.ahomewithin.ahomewithin.R;

import java.util.ArrayList;

public class MapActivity extends MainActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout flContainer = (FrameLayout) findViewById(R.id.flContent);
        View v = getLayoutInflater().inflate(R.layout.content_map, null);
        flContainer.addView(v);

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we
     * just add a marker near Africa.
     */
    @Override
    public void onMapReady(GoogleMap map) {

        // MAP_TYPE_NORMAL, MAP_TYPE_TERRAIN, MAP_TYPE_HYBRID and MAP_TYPE_NONE
        map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        map.getUiSettings().setMyLocationButtonEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);

        // center view over US
        // http://stackoverflow.com/questions/14636118/android-set-goolgemap-bounds-from-from-database-of-points
        LatLngBounds.Builder bounds = new LatLngBounds.Builder();
        bounds.include(new LatLng(24.891752, -98.4375));
        bounds.include(new LatLng(40.351289, -124.244385));
        bounds.include(new LatLng(44.488196, -70.290656));
        bounds.include(new LatLng(49.000282, -101.37085));
        map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 50));

    }


}
