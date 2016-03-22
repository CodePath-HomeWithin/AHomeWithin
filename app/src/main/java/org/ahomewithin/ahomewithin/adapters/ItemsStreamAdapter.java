package org.ahomewithin.ahomewithin.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.models.Item;
import org.ahomewithin.ahomewithin.util.ItemHomeClickListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chezlui on 06/03/16.
 */
public class ItemsStreamAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Item> mItems;
    private Context mContext;
    private AsyncHttpResponseHandler buyResponseHandler;
    private ItemHomeClickListener mListener;

    public ItemsStreamAdapter(Context context, List<Item> items, ItemHomeClickListener listener) {
        mContext = context;
        mItems = items;
        mListener = listener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater li = LayoutInflater.from(mContext);

        View itemView = li.inflate(R.layout.item_layout, parent, false);
        ItemViewHolder convertView = new ItemViewHolder(itemView);

        return convertView;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final Item item = mItems.get(position);

        ((ItemViewHolder) holder).tvTitle.setText(item.title);
        ((ItemViewHolder) holder).tvDescription.setText(item.description);

        final int resourceId = mContext.getResources().getIdentifier(
                item.imageUrl, "drawable", mContext.getPackageName());
//        ((ItemViewHolder)holder).ivItemImage.setImageDrawable(mContext.getResources().getDrawable(resourceId));


        Glide.with(mContext).load(resourceId)
                .centerCrop()
                .into(
                        ((ItemViewHolder) holder).ivItemImage);

        ((ItemViewHolder) holder).rlStreamItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onEventClicked((ItemViewHolder) holder, item);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    // Clean all elements of the recycler
    public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(List<Item> list) {
        mItems.addAll(list);
        notifyDataSetChanged();
    }


    public static class ItemViewHolder extends RecyclerView.ViewHolder {
        public @Bind(R.id.ivItemImage) ImageView ivItemImage;
        @Bind(R.id.tvTitle) TextView tvTitle;
        @Bind(R.id.tvDescription) TextView tvDescription;
        @Bind(R.id.rlStreamItem) RelativeLayout rlStreamItem;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}