package org.ahomewithin.ahomewithin.fragments;

import org.ahomewithin.ahomewithin.adapters.EventsAdapter;
import org.ahomewithin.ahomewithin.models.Event;
import org.ahomewithin.ahomewithin.models.Recommended;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.ahomewithin.ahomewithin.AHomeWithinClient;
import org.ahomewithin.ahomewithin.adapters.RecommendedAdapter;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by barbara on 3/6/16.
 */
public class EventsListFragment extends SimpleListFragment {
    ArrayList<Event> events;
    EventsAdapter eAdapter;

    public static final EventsListFragment newInstance() {
        EventsListFragment frag = new EventsListFragment();
//        Bundle args = new Bundle();
//        args.putString("value", value);
//        frag.setArguments(args);
        return frag;
    }

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = super.onCreateView(inflater, container, savedInstanceState);
        setupViews(v);
        loadEvents(0);
        return v;
    }

    protected void setupViews(View view) {
        events = new ArrayList<Event>();
        eAdapter = new EventsAdapter(events);
        super.setupViews(view, events, eAdapter);
    }

    public void loadEvents(int page) {
        setRefreshing(true);
        Event firstInList = null;
        Event lastInList = null;
        if (page < 0) {
            firstInList = (Event) getFirstInList();
        } else if (page > 0) {
            lastInList = (Event) getLastInList();
        } else {
            clear();
        }
        // TODO: use async JsonHttpResponseHandler!
        try {
            JSONObject response = AHomeWithinClient.getEvents(getContext());
            addOrInsertAll(Event.fromJSONArray(response.getJSONArray("events")));
            setRefreshing(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setRefreshing(false);


//        client.getHomeTimeline(oldestTweet, mostRecentTweet, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
//                addOrInsertAll(Tweet.fromJSONArray(response));
//                setRefreshing(false);
//            }
//
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                try {
//                    Toast.makeText(getContext(), errorResponse.toString(), Toast.LENGTH_LONG).show();
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                setRefreshing(false);
//            }
//        });
    }

    public void populateList() {
        loadEvents(0);
    }

    public void loadMore(int page, int totalItemsCount) {
        loadEvents(page);
    }

    public void refreshList() {
        loadEvents(-1);
    }
}
