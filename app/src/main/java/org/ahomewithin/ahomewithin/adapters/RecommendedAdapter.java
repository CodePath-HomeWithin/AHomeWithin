package org.ahomewithin.ahomewithin.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.models.Recommended;

import java.util.List;

/**
 * Created by barbara on 3/6/16.
 */
public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.ViewHolder>
        implements SimpleListAdapter {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvTitle;

        public ViewHolder(View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
        }
    }

    private List<Recommended> recommendations;

    // Pass in the contact array into the constructor
    public RecommendedAdapter(List<Recommended> items) {
        recommendations = items;
    }

   @Override
    public RecommendedAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View contactView = inflater.inflate(R.layout.item_recommended, parent, false);
        return (new ViewHolder(contactView));
    }

    @Override
    public void onBindViewHolder(RecommendedAdapter.ViewHolder viewHolder, int position) {
        Recommended item = recommendations.get(position);
        viewHolder.tvTitle.setText(item.getTitle());
    }

    public RecyclerView.Adapter getAdapter() {
        return this;
    }

    public int getItemCount() {
        return recommendations.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        recommendations.clear();
        notifyDataSetChanged();
    }

    @SuppressWarnings("unchecked")
    private List<Recommended> convert(List<?> items) {
        return (List<Recommended>) items;
    }

    // Add a list of items
    public void addAll(List<?> items) {
        int curSize = recommendations.size();
        recommendations.addAll(convert(items));
        notifyItemRangeInserted(curSize, recommendations.size() - 1);
    }

    public void insertAll(List<?> items) {
        recommendations.addAll(0, convert(items));
        notifyItemRangeInserted(0, items.size() - 1);
    }

    public void addOrInsertAll(List<?> items) {
        if ((items.size() > 0) && (recommendations.size() > 0)) {
            Recommended item = (Recommended) items.get(0);
            if (item.isMoreRecent(recommendations.get(0))) {
                insertAll(items);
                return;
            }
        }
        addAll(items);
    }
}
