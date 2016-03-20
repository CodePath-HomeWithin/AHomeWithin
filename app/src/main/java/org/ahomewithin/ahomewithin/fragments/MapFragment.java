package org.ahomewithin.ahomewithin.fragments;

/**
 * Created by barbara on 3/12/16.
 */

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.util.MapMarkers;
import org.ahomewithin.ahomewithin.util.PermissionUtils;

public class MapFragment extends Fragment implements
        GoogleMap.OnMyLocationButtonClickListener,
        OnMapReadyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback,
        CustomMapFragment.OnMapFragmentReadyListener
{

    /**
     * Request code for location permission request.
     *
     * @see #onRequestPermissionsResult(int, String[], int[])
     */
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    /**
     * Flag indicating whether a requested permission has been denied after returning in
     * {@link #onRequestPermissionsResult(int, String[], int[])}.
     */
    private boolean mPermissionDenied = false;

    private android.app.FragmentManager fm;
    private GoogleMap mMap;
    private MapMarkers mMapMarkers;
    private SupportMapFragment mMapFragment;
    private SlidingUpPanelLayout mSlidePanel;

    public static MapFragment newInstance() {
        return new MapFragment();
    }


    // http://stackoverflow.com/questions/25051246/how-to-use-supportmapfragment-inside-a-fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.content_map, container, false);
        mSlidePanel = (SlidingUpPanelLayout) v.findViewById(R.id.sliding_layout);

        // Close SlidingPanel when back button pressed
        v.setFocusableInTouchMode(true);
        v.requestFocus();
        v.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(keyCode == KeyEvent.KEYCODE_BACK) {
                    if (mSlidePanel.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
                        mSlidePanel.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                        return true;
                    }
                }
                return false;
            }
        });


        mMapFragment = CustomMapFragment.newInstance();
        getChildFragmentManager().beginTransaction().replace(R.id.flMapContainer, mMapFragment).commit();
        mMapMarkers = new MapMarkers(getActivity());
        return v;
    }

    /*
     * This get's called from within CustomMapFragment, which expects a
     * CustomMapFragment.OnMapFragmentReadyListener
     */
    public void onMapFragmentReady() {
        mMapFragment.getMapAsync(this);
    }

    @Override
    // SF location:  37.772123, -122.405293
    public void onMapReady(GoogleMap map) {
        mMap = map;

        // MAP_TYPE_NORMAL, MAP_TYPE_TERRAIN, MAP_TYPE_HYBRID and MAP_TYPE_NONE
        //map.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        map.getUiSettings().setMyLocationButtonEnabled(true); // button in upper right of map
        map.getUiSettings().setZoomControlsEnabled(true);     // zoom controls in lower right of map

        // center view over US
        // http://stackoverflow.com/questions/14636118/android-set-goolgemap-bounds-from-from-database-of-points


        if (mMapMarkers != null) {
            mMapMarkers.addMarkersToMap(mMap);

            LatLngBounds.Builder bounds = new LatLngBounds.Builder();
            for (Marker marker : mMapMarkers.getMarkers()) {
                bounds.include(marker.getPosition());
            }
            map.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds.build(), 40));

            enableMyLocation();
        }
    }

    /**
     * Enables the My Location layer if the fine location permission has been granted.
     * // https://gist.github.com/MariusVolkhart/618a51bb09c4fc7f86a4
     */
    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            this.requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else if (mMap != null) {
            // Access to the location has been granted to the app.
            mMap.setMyLocationEnabled(true);

            // zoom map to current location, if known
            LocationManager locationManager =
                    (LocationManager)getActivity().getSystemService(Context.LOCATION_SERVICE);
            Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());
                CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 11);
                mMap.animateCamera(cameraUpdate);
            }
        }
    }

    @Override
    public boolean onMyLocationButtonClick() {
        // Return false so that we don't consume the event and the default behavior till occurs
        // (the camera animates to the user's current position).
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mPermissionDenied = true;
        }
    }

    /**
     * Displays a dialog with error message explaining that the location permission is missing.
     */
    private void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog
                .newInstance(true).show(getActivity().getSupportFragmentManager(), "dialog");
    }
}

