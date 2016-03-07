package org.ahomewithin.ahomewithin.adapters;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by barbara on 3/6/16.
 */
public interface SimpleListAdapter {
    public abstract RecyclerView.Adapter getAdapter();
    public abstract int getItemCount();
    public abstract void clear();
    public abstract void addAll(List<?> items);
    public abstract void insertAll(List<?> items);
    public abstract void addOrInsertAll(List<?> items);
}
