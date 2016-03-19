package org.ahomewithin.ahomewithin.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.adapters.SimpleListAdapter;
import org.ahomewithin.ahomewithin.views.DividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by barbara on 3/6/16.
 */
public abstract class SimpleListFragment extends Fragment {
    ArrayList<?> listOfItems;
    SimpleListAdapter itemsAdapter;
    RecyclerView rvItems;
    SwipeRefreshLayout swipeContainer;

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_simple_list, container, false);
    }

    protected void setupViews(View view, ArrayList<?> itemsList, SimpleListAdapter adapter) {
        listOfItems = itemsList;
        itemsAdapter = adapter;
        rvItems = (RecyclerView) view.findViewById(R.id.rvItems);
        rvItems.setAdapter(itemsAdapter.getAdapter());
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());


//        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//        layoutManager.scrollToPosition(0);
//        rvItems.setLayoutManager(layoutManager);

        // First param is number of columns and second param is orientation i.e Vertical or Horizontal
        StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
// Attach the layout manager to the recycler view
        rvItems.setLayoutManager(gridLayoutManager);

        RecyclerView.ItemDecoration itemDecoration = new
                DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL_LIST);
        rvItems.addItemDecoration(itemDecoration);
        rvItems.addOnScrollListener(new EndlessRecyclerViewScrollListener(layoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                loadMore(page, totalItemsCount);
            }
        });

        swipeContainer = (SwipeRefreshLayout) view.findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshList();
            }
        });
    }

    public void setRefreshing(boolean value) {
        swipeContainer.setRefreshing(value);
    }

    public Object getFirstInList() {
        if (listOfItems.size() > 0) {
            return listOfItems.get(0);
        }
        return null;
    }

    public Object getLastInList() {
        if (listOfItems.size() > 0) {
            return listOfItems.get(listOfItems.size() - 1);
        }
        return null;
    }

    public void clear() {
        itemsAdapter.clear();
    }

    public void addOrInsertAll(List<?> items) {
        itemsAdapter.addOrInsertAll(items);
    }

    abstract void populateList();
    abstract void loadMore(int page, int totalItemsCount);
    abstract void refreshList();
}
