package org.ahomewithin.ahomewithin.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.models.Event;
import org.ahomewithin.ahomewithin.models.Item;
import org.ahomewithin.ahomewithin.util.UserTools;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by barbara on 3/19/16.
 */
public class EventDetailFragment extends Fragment {
    Event mEvent;

    public static final String LOG_TAG = DetailFragment.class.getSimpleName();


    public static EventDetailFragment newInstance(Event event) {
        Bundle args = new Bundle();
//        args.putParcelable(event);
        EventDetailFragment fragment = new EventDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View convertView = inflater.inflate(R.layout.fragment_event_detail, container, false);
        ButterKnife.bind(this, convertView);
//        mItem = (Event) getArguments().getSerializable(Event.SERIALIZABLE_TAG);
        return convertView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//        Glide.with(getActivity()).load(mItem.imageUrl).into(ivItemImage);
//        tvTitle.setText(mItem.title);
//        tvDescription.setText(mItem.description);
//
//        // Calculate again to force update
//        mItem.owned = UserTools.isItemPurchased(getActivity(), mItem.id);
//        if (mItem.owned) {
//            btBuy.setText("WATCH NOW");
//        } else {
//            btBuy.setText("BUY NOW");
//        }
//
//        btBuy.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (mItem.owned) {
//                    onWatch();
//                } else {
//                    onBuy();
//                }
//            }
//        });
    }

}
