package org.ahomewithin.ahomewithin.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.models.Recommended;

import java.util.List;

/**
 * Created by barbara on 3/6/16.
 */
public class RecommendedAdapter extends RecyclerView.Adapter<RecommendedAdapter.ViewHolder>
        implements SimpleListAdapter {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivImageType;
        public ImageView ivThumbnail;
        public TextView tvTitle;
        public TextView tvSummary;
        public TextView tvPublishDate;

        public ViewHolder(View itemView) {
            super(itemView);
            ivImageType = (ImageView) itemView.findViewById(R.id.ivImageType);
            ivThumbnail = (ImageView) itemView.findViewById(R.id.ivThumbnail);
            tvTitle = (TextView) itemView.findViewById(R.id.tvTitle);
            tvSummary = (TextView) itemView.findViewById(R.id.tvSummary);
            tvPublishDate = (TextView) itemView.findViewById(R.id.tvPublishDate);
        }
    }

    private Context mContext;
    private List<Recommended> recommendations;

    // Pass in the contact array into the constructor
    public RecommendedAdapter(Context context, List<Recommended> items) {
        mContext = context;
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
        viewHolder.tvSummary.setText(item.getSummary());
        viewHolder.tvPublishDate.setText(item.getFormattedPublishDate());

        if (item.getThumbnailUrl() != null) {
            Glide.with(mContext).load(item.getThumbnailUrl()).into(viewHolder.ivThumbnail);
        }
        switch(item.type) {
            case BOOK:
                Glide.with(mContext).load(R.drawable.ic_more_book).into(viewHolder.ivImageType);
                break;
            case PODCAST:
                Glide.with(mContext).load(R.drawable.ic_more_podcast).into(viewHolder.ivImageType);
                break;
            case BLOG:
                Glide.with(mContext).load(R.drawable.ic_more_blog).into(viewHolder.ivImageType);
                break;
            default:
                // other
                Glide.with(mContext).load(R.drawable.ic_more_book).into(viewHolder.ivImageType);
        }
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
