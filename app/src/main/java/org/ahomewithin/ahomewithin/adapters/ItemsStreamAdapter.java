package org.ahomewithin.ahomewithin.adapters;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.activities.DetailFragment;
import org.ahomewithin.ahomewithin.models.Item;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chezlui on 06/03/16.
 */
public class ItemsStreamAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<Item> mItems;
    private Context mContext;
    private OnItemInteraction mListener;
    private AsyncHttpResponseHandler buyResponseHandler;


    public interface OnItemInteraction {
        public void onBuy(int position);    // TODO change to id
    }

    public ItemsStreamAdapter(Context context, List<Item> items, OnItemInteraction listener) {
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
        Glide.with(mContext).load(item.imageUrl).into(
                ((ItemViewHolder) holder).ivItemImage);

        ((ItemViewHolder) holder).tvSeeMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(((ItemViewHolder)holder).tvDescription.getVisibility() == View.VISIBLE) {
                    ((ItemViewHolder)holder).tvDescription.setVisibility(View.GONE);
                    ((ItemViewHolder)holder).tvSeeMore.setText("See more");
                } else {
                    ((ItemViewHolder) holder).tvDescription.setVisibility(View.VISIBLE);
                    ((ItemViewHolder) holder).tvSeeMore.setText("See less");
                }
            }
        });

        if (item.owned) {
            ((ItemViewHolder)holder).tvBuy.setText("WATCH");
        } else {
            ((ItemViewHolder)holder).tvBuy.setText("BUY");
        }

        ((ItemViewHolder)holder).tvBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(item.owned) {
                    ((AppCompatActivity)mContext)
                            .getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.flContent, DetailFragment.newInstance(item))
                            .addToBackStack(null)
                            .commit();
                } else {
                    mListener.onBuy(position);
                }
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
        @Bind(R.id.ivItemImage) ImageView ivItemImage;
        @Bind(R.id.tvTitle) TextView tvTitle;
        @Bind(R.id.tvDescription) TextView tvDescription;
        @Bind(R.id.tvSeeMore) TextView tvSeeMore;
        @Bind(R.id.tvBuy) TextView tvBuy;

        public ItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}