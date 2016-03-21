package org.ahomewithin.ahomewithin.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.models.Event;
import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by barbara on 3/19/16.
 */
public class EventDetailFragment extends Fragment {
    public static final String FRAGMENT_TAG = EventDetailFragment.class.getSimpleName();
    public static final String LOG_TAG = DetailFragment.class.getSimpleName();

    Event mEvent;

    Context mContext;
    @Bind(R.id.tvGroupName) TextView tvGroupName;
    @Bind(R.id.tvEventName) TextView tvEventName;
    @Bind(R.id.tvEventDescription) TextView tvEventDescription;
    @Bind(R.id.tvDateTime) TextView tvDateTime;
    @Bind(R.id.ivImage) ImageView ivImage;



    @Bind(R.id.ivLink) ImageView ivLink;


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

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            convertView.findViewById(R.id.ivImage).setTransitionName("event_image_transition");
        }

        mContext = getContext();
        mEvent = (Event) Parcels.unwrap(getArguments().getParcelable(Event.SERIALIZABLE_TAG));
        return convertView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Glide.with(getActivity()).load(mEvent.imageUrl).into(ivImage);
        tvGroupName.setText(mEvent.groupName);
        tvEventName.setText(mEvent.eventName);
        String desc = mEvent.eventDescription;
        if ((desc == null) || (desc.length() == 0)) {
            desc = mEvent.groupDescription;
        }
        tvEventDescription.setText(desc);
        tvDateTime.setText(mEvent.getDateTime());
        ivLink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mEvent.getUrl()));
                mContext.startActivity(intent);
            }
        });
    }

}
