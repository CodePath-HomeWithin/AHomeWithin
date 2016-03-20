package org.ahomewithin.ahomewithin.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.ahomewithin.ahomewithin.AHomeWithinClient;
import org.ahomewithin.ahomewithin.adapters.EventsAdapter;
import org.ahomewithin.ahomewithin.models.Event;
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
        eAdapter = new EventsAdapter(getContext(), events);
        super.setupViews(view, events, eAdapter);
    }

    public  void bindRecycleViewToLayoutManager(RecyclerView rvItems) {
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        rvItems.setLayoutManager(gridLayoutManager);

        rvItems.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                loadMore(page, totalItemsCount);
            }
        });
    }

    public void loadEvents(int page) {
        setRefreshing(true);
        Event firstInList = null;
        Event lastInList = null;
        // TODO: use async JsonHttpResponseHandler!
        try {
            if (page < 0) {
                firstInList = (Event) getFirstInList();
                // TODO: load more recent events
            } else if (page > 0) {
                lastInList = (Event) getLastInList();
                // TODO:  load next pages of events
            } else {
                clear();
                JSONObject response = AHomeWithinClient.getEvents(getContext());
                addOrInsertAll(Event.fromJSONArray(response.getJSONArray("events")));
            }
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
