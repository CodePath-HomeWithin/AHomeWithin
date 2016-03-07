package org.ahomewithin.ahomewithin.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.models.Event;

import java.util.List;

/**
 * Created by barbara on 3/6/16.
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder>
        implements SimpleListAdapter {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvName;

        public ViewHolder(View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvName);
        }
    }

    private List<Event> events;

    public EventsAdapter(List<Event> items) {
        events = items;
    }

    @Override
    public EventsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View contactView = inflater.inflate(R.layout.item_event, parent, false);
        return (new ViewHolder(contactView));
    }

    @Override
    public void onBindViewHolder(EventsAdapter.ViewHolder viewHolder, int position) {
        Event item = events.get(position);
        viewHolder.tvName.setText(item.name);
    }

    public RecyclerView.Adapter getAdapter() {
        return this;
    }

    public int getItemCount() {
        return events.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        events.clear();
        notifyDataSetChanged();
    }

    @SuppressWarnings("unchecked")
    private List<Event> convert(List<?> items) {
        return (List<Event>) items;
    }

    // Add a list of items
    public void addAll(List<?> items) {
        int curSize = events.size();
        events.addAll(convert(items));
        notifyItemRangeInserted(curSize, events.size() - 1);
    }

    public void insertAll(List<?> items) {
        events.addAll(0, convert(items));
        notifyItemRangeInserted(0, items.size() - 1);
    }

    public void addOrInsertAll(List<?> items) {
        if ((items.size() > 0) && (events.size() > 0)) {
            Event item = (Event) items.get(0);
            if (item.isMoreRecent(events.get(0))) {
                insertAll(items);
                return;
            }
        }
        addAll(items);
    }
}

