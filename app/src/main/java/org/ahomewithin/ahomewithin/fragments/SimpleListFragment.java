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
import org.ahomewithin.ahomewithin.views.ItemOffsetDecoration;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by barbara on 3/6/16.
 */
public abstract class SimpleListFragment extends Fragment {
    public static final String FRAGMENT_TAG = SimpleListFragment.class.getSimpleName();

    ArrayList<?> listOfItems;
    SimpleListAdapter itemsAdapter;
    RecyclerView rvItems;
    SwipeRefreshLayout swipeContainer;

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_simple_list, container, false);
    }

    public int numGridColumns() {
        return(1);
    };

    public RecyclerView.ItemDecoration itemDecoration() {
        return null;
    }

    protected void setupViews(View view, ArrayList<?> itemsList, SimpleListAdapter adapter) {
        listOfItems = itemsList;
        itemsAdapter = adapter;
        rvItems = (RecyclerView) view.findViewById(R.id.rvItems);
        rvItems.setAdapter(itemsAdapter.getAdapter());

        final StaggeredGridLayoutManager gridLayoutManager =
                new StaggeredGridLayoutManager(numGridColumns(), StaggeredGridLayoutManager.VERTICAL);
        rvItems.setLayoutManager(gridLayoutManager);

        RecyclerView.ItemDecoration itemDecoration = itemDecoration();
        if (itemDecoration != null) {
            rvItems.addItemDecoration(itemDecoration);
        }

        rvItems.addOnScrollListener(new EndlessRecyclerViewScrollListener(gridLayoutManager) {
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
