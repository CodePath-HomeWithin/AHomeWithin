package org.ahomewithin.ahomewithin.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.fragments.StreamPagerFragment;

/**
 * Created by chezlui on 06/03/16.
 *
 * The actitivy can be called with an argument {@link org.ahomewithin.ahomewithin.fragments.StreamPagerFragment#ARG_STREAM_TAB}
 * that would indicate the stream view that it should be showed when opening, having two choices:
 * - {@link org.ahomewithin.ahomewithin.fragments.StreamPagerFragment.StreamsPagerAdapter#VIDEOS}
 * - {@link org.ahomewithin.ahomewithin.fragments.StreamPagerFragment.StreamsPagerAdapter#CONVERSATIONS}
 *
 */

public class StreamActivity extends MainActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.flContent, new StreamPagerFragment())
                .addToBackStack(null);
        ft.commit();
    }
}
