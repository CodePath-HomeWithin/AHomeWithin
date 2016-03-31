package org.ahomewithin.ahomewithin.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.ahomewithin.ahomewithin.AHomeWithinClient;
import org.ahomewithin.ahomewithin.adapters.RecommendedAdapter;
import org.ahomewithin.ahomewithin.models.Recommended;
import org.ahomewithin.ahomewithin.views.DividerItemDecoration;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by barbara on 3/6/16.
 */
public class RecommendedFragment extends SimpleListFragment {
    public static final String FRAGMENT_TAG = RecommendedFragment.class.getSimpleName();

    ArrayList<Recommended> recommendations;
    RecommendedAdapter rAdapter;

    public static final RecommendedFragment newInstance() {
        RecommendedFragment frag = new RecommendedFragment();
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
        loadRecommendations(0);
        return v;
    }

    protected void setupViews(View view) {
        recommendations = new ArrayList<Recommended>();
        rAdapter = new RecommendedAdapter(getContext(), recommendations);
        super.setupViews(view, recommendations, rAdapter);
    }

    public void loadRecommendations(int page) {
        setRefreshing(true);
        Recommended firstInList = null;
        Recommended lastInList = null;
        // TODO: use async JsonHttpResponseHandler!
        try {
            if (page < 0) {
                firstInList = (Recommended) getFirstInList();
                // TODO:  load any newer pages
            } else if (page > 0) {
                lastInList = (Recommended) getLastInList();
                // TODO:  load later pages
            } else {
                clear();
                JSONObject response = AHomeWithinClient.getRecommendations(getContext());
                addOrInsertAll(Recommended.fromJSONArray(response.getJSONArray("recommendations")));
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
        loadRecommendations(0);
    }

    public void loadMore(int page, int totalItemsCount) {
        loadRecommendations(page);
    }

    public void refreshList() {
        loadRecommendations(-1);
    }
}
