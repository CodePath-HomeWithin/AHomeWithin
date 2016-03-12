package org.ahomewithin.ahomewithin.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.ahomewithin.ahomewithin.R;

/**
 * Created by barbara on 3/4/16.
 */
public class ServicesFragment extends Fragment {

    public static ServicesFragment newInstance() {
        ServicesFragment servicesFragment = new ServicesFragment();
        return servicesFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View convertView = inflater.inflate(R.layout.content_services, container, false);

        if (savedInstanceState == null) {
            EventsListFragment fragmentEvents = EventsListFragment.newInstance();
            getFragmentManager().beginTransaction()
                    .replace(R.id.flEvents, fragmentEvents).commit();
        }

        return convertView;
    }


//
//    private void loadProviders() {
//        try {
//            JSONObject jsonObj = AHomeWithinClient.getProviders(this);
//            List<Provider> providers = Provider.fromJSONArray(jsonObj.getJSONArray("providers"));
//            System.out.println(providers.toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }


}