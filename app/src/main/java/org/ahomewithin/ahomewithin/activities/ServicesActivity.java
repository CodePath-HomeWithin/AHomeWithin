package org.ahomewithin.ahomewithin.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;

import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.fragments.EventsListFragment;

/**
 * Created by barbara on 3/4/16.
 */
public class ServicesActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout flContainer = (FrameLayout) findViewById(R.id.flContent);
        View v = getLayoutInflater().inflate(R.layout.content_services, null);
        flContainer.addView(v);

        if (savedInstanceState == null) {
            EventsListFragment fragmentEvents = EventsListFragment.newInstance();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.flEvents, fragmentEvents).commit();
        }
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
