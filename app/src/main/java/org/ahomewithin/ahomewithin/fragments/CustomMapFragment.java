package org.ahomewithin.ahomewithin.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.SupportMapFragment;

/**
 * Created by barbara on 3/12/16.
 * http://stackoverflow.com/questions/13733299/initialize-mapfragment-programmatically-with-maps-api-v2
 */
public class CustomMapFragment extends SupportMapFragment {

    /**
     * Listener interface to tell when the map is ready
     */
    public static interface OnMapFragmentReadyListener {

        void onMapFragmentReady();
    }

    public CustomMapFragment() {
        super();
    }

    public static CustomMapFragment newInstance() {
        CustomMapFragment fragment = new CustomMapFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater arg0, ViewGroup arg1, Bundle arg2) {
        View v = super.onCreateView(arg0, arg1, arg2);
        Fragment fragment = getParentFragment();
        if (fragment != null && fragment instanceof OnMapFragmentReadyListener) {
            ((OnMapFragmentReadyListener) fragment).onMapFragmentReady();
        }
        return v;
    }
}