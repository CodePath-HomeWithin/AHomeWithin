package org.ahomewithin.ahomewithin.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
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
    @Bind(R.id.viewpager) ViewPager viewPager;
    @Bind(R.id.pagerSlidingTabs) PagerSlidingTabStrip pagerSlidingTabs;

    public static final String ARG_STREAM_TAB = "stream";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View convertView = inflater.inflate(R.layout.content_stream_pager, container, false);
        ButterKnife.bind(this, convertView);

        return convertView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewPager.setAdapter(new StreamsPagerAdapter(getFragmentManager()));

        if (getActivity().getIntent().hasExtra(ARG_STREAM_TAB)) {
            viewPager.setCurrentItem(getActivity().getIntent().getIntExtra(ARG_STREAM_TAB, 0));
        }

        pagerSlidingTabs.setViewPager(viewPager);
    }


    // Return the order of the fragments in the viewpager
    public class StreamsPagerAdapter extends FragmentPagerAdapter {
        public static final int VIDEOS = 0;
        public static final int CONVERSATIONS = 1;
        private String tabTitles[] = {"VIDEOS", "CONVERSATIONS"};

        public StreamsPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == VIDEOS) {
                return StreamFragment.newInstance(VIDEOS);
            } else if (position == CONVERSATIONS) {
                return StreamFragment.newInstance(CONVERSATIONS);
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
