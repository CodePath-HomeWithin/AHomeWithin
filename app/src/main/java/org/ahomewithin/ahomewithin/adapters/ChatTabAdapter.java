package org.ahomewithin.ahomewithin.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by xiangyang_xiao on 3/20/16.
 */
public class ChatTabAdapter extends FragmentPagerAdapter {

    private String[] titles;
    private Fragment[] fragments;

    public ChatTabAdapter(
        String[] titles,
        Fragment[] fragments,
        FragmentManager fragmentManager
    ) {
        super(fragmentManager);
        this.titles = titles;
        this.fragments = fragments;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return fragments.length;
    }
}
