package org.ahomewithin.ahomewithin.activities;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import org.ahomewithin.ahomewithin.AHomeWithinClient;
import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.models.Event;
import org.ahomewithin.ahomewithin.models.Provider;
import org.json.JSONObject;

import java.util.List;

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
    }

    @Override
    public View onCreateView(View parent, String name, Context context, AttributeSet attrs) {
        View v = super.onCreateView(parent, name, context, attrs);
        loadProviders();
        loadEvents();
        return v;
    }

    private void loadProviders() {
        try {
            JSONObject jsonObj = AHomeWithinClient.getProviders(this);
            List<Provider> providers = Provider.fromJSONArray(jsonObj.getJSONArray("providers"));
            System.out.println(providers.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadEvents() {
        try {
            JSONObject jsonObj = AHomeWithinClient.getEvents(this);
            List<Event> events = Event.fromJSONArray(jsonObj.getJSONArray("events"));
            System.out.println(events.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
