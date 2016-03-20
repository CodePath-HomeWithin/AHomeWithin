package org.ahomewithin.ahomewithin.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.models.Event;
import org.ahomewithin.ahomewithin.models.Item;
import org.ahomewithin.ahomewithin.util.UserTools;
import org.parceler.Parcels;
import org.w3c.dom.Text;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by barbara on 3/19/16.
 */
public class EventDetailFragment extends Fragment {
    Event mEvent;

    @Bind(R.id.tvGroupName) TextView tvGroupName;
    @Bind(R.id.tvEventName) TextView tvEventName;
    @Bind(R.id.tvEventDescription) TextView tvEventDescription;
    @Bind(R.id.tvDateTime) TextView tvDateTime;
    @Bind(R.id.ivImage) ImageView ivImage;

    public static final String LOG_TAG = DetailFragment.class.getSimpleName();


    public static EventDetailFragment newInstance(Event event) {
        Bundle args = new Bundle();
        args.putParcelable(Event.SERIALIZABLE_TAG, Parcels.wrap(event));
        EventDetailFragment fragment = new EventDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View convertView = inflater.inflate(R.layout.fragment_event_detail, container, false);
        ButterKnife.bind(this, convertView);
        mEvent = (Event) Parcels.unwrap(getArguments().getParcelable(Event.SERIALIZABLE_TAG));
        return convertView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Glide.with(getActivity()).load(mEvent.imageUrl).into(ivImage);
        tvGroupName.setText(mEvent.groupName);
        tvEventName.setText(mEvent.eventName);
        tvEventDescription.setText(mEvent.eventDescription);
        tvDateTime.setText(mEvent.getDateTime());
    }

}
