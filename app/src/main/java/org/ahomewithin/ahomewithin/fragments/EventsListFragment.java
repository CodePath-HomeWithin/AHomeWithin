package org.ahomewithin.ahomewithin.fragments;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.ahomewithin.ahomewithin.AHomeWithinClient;
import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.adapters.EventsAdapter;
import org.ahomewithin.ahomewithin.models.Event;
import org.ahomewithin.ahomewithin.util.DetailsTransition;
import org.ahomewithin.ahomewithin.util.EventClickListener;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;

/**
 * Created by barbara on 3/6/16.
 */
public class EventsListFragment extends SimpleListFragment implements EventClickListener {
    public static final String FRAGMENT_TAG = EventsListFragment.class.getSimpleName();

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
        eAdapter = new EventsAdapter(getContext(), events, this);
        super.setupViews(view, events, eAdapter);
    }

    public int numGridColumns() {
        return(3);
    };

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
                addOrInsertAll(Event.fromJSONArray(response.getJSONArray("events")));
                addOrInsertAll(Event.fromJSONArray(response.getJSONArray("events")));
                addOrInsertAll(Event.fromJSONArray(response.getJSONArray("events")));
                addOrInsertAll(Event.fromJSONArray(response.getJSONArray("events")));
                addOrInsertAll(Event.fromJSONArray(response.getJSONArray("events")));
                addOrInsertAll(Event.fromJSONArray(response.getJSONArray("events")));
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

    @Override
    public void onEventClicked(EventsAdapter.ViewHolder holder, Event event) {

        EventDetailFragment detailFragment = EventDetailFragment.newInstance(event);

        // Note that we need the API version check here because the actual transition classes (e.g. Fade)
        // are not in the support library and are only available in API 21+. The methods we are calling on the Fragment
        // ARE available in the support library (though they don't do anything on API < 21)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            detailFragment.setSharedElementEnterTransition(new DetailsTransition());
            detailFragment.setEnterTransition(new Fade());
            setExitTransition(new Fade());
            setSharedElementReturnTransition(new DetailsTransition());
        }

        ViewCompat.setTransitionName(holder.ivImage, getActivity().getString(R.string.even_transition));

        Bundle bundle = new Bundle();
        bundle.putParcelable(Event.SERIALIZABLE_TAG, Parcels.wrap(event));
        detailFragment.setArguments(bundle);
        getActivity().getSupportFragmentManager()
                .beginTransaction()
                .addSharedElement(holder.ivImage, getActivity().getString(R.string.even_transition))
                .replace(R.id.flContent, detailFragment, DetailFragment.FRAGMENT_TAG)
                .addToBackStack(null)
                .commit();
    }
}
