package org.ahomewithin.ahomewithin.adapters;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.fragments.EventDetailFragment;
import org.ahomewithin.ahomewithin.fragments.EventsFragment;
import org.ahomewithin.ahomewithin.models.Event;
import org.parceler.Parcels;

import java.util.List;

/**
 * Created by barbara on 3/6/16.
 */
public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.ViewHolder>
        implements SimpleListAdapter {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout llEventContainer;
        public TextView tvEventName;
        public ImageView ivImage;
        public TextView tvDescription;
        public TextView tvDateTime;

        public ViewHolder(View itemView) {
            super(itemView);
            llEventContainer = (LinearLayout) itemView.findViewById(R.id.llEventContainer);
            tvEventName = (TextView) itemView.findViewById(R.id.tvEventName);
            tvDescription = (TextView) itemView.findViewById(R.id.tvDescription);
            tvDateTime = (TextView) itemView.findViewById(R.id.tvDateTime);
            ivImage = (ImageView) itemView.findViewById(R.id.ivImage);
        }
    }

    private Context mContext;
    private List<Event> events;

    public EventsAdapter(Context context, List<Event> items) {
        mContext = context;
        events = items;
    }

    @Override
    public EventsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View contactView = inflater.inflate(R.layout.item_event, parent, false);
        return (new ViewHolder(contactView));
    }

    @Override
    public void onBindViewHolder(final EventsAdapter.ViewHolder viewHolder, int position) {
        final Event event = events.get(position);
        viewHolder.tvEventName.setText(event.eventName);
        viewHolder.tvDateTime.setText(event.getDateTime());
        Glide.with(mContext)
                .load(event.imageUrl)
                .fitCenter()
                .into(viewHolder.ivImage);

        String url = event.getUrl();
        if (url != null) {
            // http://www.androidauthority.com/using-shared-element-transitions-activities-fragments-631996/
            viewHolder.llEventContainer.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    EventsFragment currentEventsFragment =
                            (EventsFragment) ((AppCompatActivity) mContext).getSupportFragmentManager().
                                    findFragmentByTag(EventsFragment.FRAGMENT_TAG);
                    EventDetailFragment detailFragment;

                    if (currentEventsFragment != null) {
                        ImageView staticImage = (ImageView) v.findViewById(R.id.ivImage);

                        detailFragment = new EventDetailFragment();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            currentEventsFragment.setSharedElementReturnTransition(TransitionInflater.from(
                                    (AppCompatActivity) mContext).inflateTransition(R.transition.change_image_trans));
                            currentEventsFragment.setExitTransition(TransitionInflater.from(
                                    (AppCompatActivity) mContext).inflateTransition(android.R.transition.fade));

                            detailFragment.setSharedElementEnterTransition(TransitionInflater.from(
                                    (AppCompatActivity) mContext).inflateTransition(R.transition.change_image_trans));
                            detailFragment.setEnterTransition(TransitionInflater.from(
                                    (AppCompatActivity) mContext).inflateTransition(android.R.transition.fade));
                        }

                        Bundle bundle = new Bundle();
                        bundle.putParcelable(Event.SERIALIZABLE_TAG, Parcels.wrap(event));
//                        bundle.putString("ACTION", viewHolder.tvEventName.getText().toString());
//                        bundle.putParcelable("IMAGE", ((BitmapDrawable) viewHolder.ivImage.getDrawable()).getBitmap());
                        detailFragment.setArguments(bundle);
                        ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.flContent, detailFragment)
                                .addToBackStack("Event")
                                .addSharedElement(staticImage, "event_image_transition")
                                .commit();
                    } else {
                        detailFragment = EventDetailFragment.newInstance(event);
                        ((AppCompatActivity) mContext).getSupportFragmentManager().beginTransaction()
                                .replace(R.id.flContent, detailFragment)
                                .addToBackStack("Event")
                                .commit();
                    }
                }
            });

        }
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

