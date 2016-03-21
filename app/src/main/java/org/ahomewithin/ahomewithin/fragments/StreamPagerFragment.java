package org.ahomewithin.ahomewithin.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;

import org.ahomewithin.ahomewithin.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chezlui on 06/03/16.
 */
public class StreamPagerFragment extends Fragment {
    public static final String FRAGMENT_TAG = StreamPagerFragment.class.getSimpleName();

    public enum ViewType {
        STORE, LIBRARY
    }

    @Bind(R.id.viewpager) ViewPager viewPager;
    @Bind(R.id.pagerSlidingTabs) PagerSlidingTabStrip pagerSlidingTabs;

    public static final String ARG_STREAM_TAB = "stream";
    public static final String ARG_OWNED_ITEMS = "my_library";

    private ViewType mType;
    private boolean showOnlyOwned;

    public static StreamPagerFragment newInstance(ViewType type, boolean showOnlyOwned) {
        StreamPagerFragment streamFragment = new StreamPagerFragment();
        Bundle args = new Bundle();
        args.putInt("type", type.ordinal());
        args.putBoolean(ARG_OWNED_ITEMS, showOnlyOwned);
        streamFragment.setArguments(args);
        return streamFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        Bundle bundle = this.getArguments();
        int typeIndex = bundle.getInt("type", ViewType.STORE.ordinal());
        mType = ViewType.values()[typeIndex];
        showOnlyOwned = bundle.getBoolean(ARG_OWNED_ITEMS);
        initForType(mType);

        View convertView = inflater.inflate(R.layout.content_stream_pager, container, false);
        convertView.setFocusableInTouchMode(true);
        convertView.requestFocus();
        convertView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(keyCode == KeyEvent.KEYCODE_BACK) {
                    getActivity().getSupportFragmentManager().popBackStack();
                    return true;
                }
                return false;
            }
        });


        ButterKnife.bind(this, convertView);

        return convertView;
    }

    private void initForType(ViewType type) {
        switch(type) {
            case LIBRARY:
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.library);
                break;
            case STORE:
                ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.training_and_tools);
                break;
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager.setAdapter(new StreamsPagerAdapter(getChildFragmentManager()));

        if (getActivity().getIntent().hasExtra(ARG_STREAM_TAB)) {
            viewPager.setCurrentItem(getActivity().getIntent().getIntExtra(ARG_STREAM_TAB, 0));
        }

        pagerSlidingTabs.setViewPager(viewPager);
    }


    // Return the order of the fragments in the viewpager
    public class StreamsPagerAdapter extends FragmentStatePagerAdapter {
        public static final int VIDEOS = 0;
        public static final int CONVERSATIONS = 1;
        private String tabTitles[] = {"VIDEOS", "CONVERSATIONS"};

        public StreamsPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == VIDEOS) {
                return StreamFragment.newInstance(VIDEOS, showOnlyOwned);
            } else if (position == CONVERSATIONS) {
                return StreamFragment.newInstance(CONVERSATIONS, showOnlyOwned);
            } else {
                return null;
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }
    }
}
