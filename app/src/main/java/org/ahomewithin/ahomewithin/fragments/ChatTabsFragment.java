package org.ahomewithin.ahomewithin.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.astuetz.PagerSlidingTabStrip;

import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.adapters.ChatTabAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xiangyang_xiao on 3/20/16.
 */
public class ChatTabsFragment extends Fragment {

    public static final String FRAGMENT_TAG = ChatTabsFragment.class.getSimpleName();
    private ChatRoomFragment chatRoomFragment = null;
    private ChatHistoryFragment chatHistoryFragment = null;

    @Bind(R.id.viewpager)
    ViewPager vpViewPager;

    @Bind(R.id.tabs)
    PagerSlidingTabStrip tabStrip;

    public static ChatTabsFragment newInstance() {
        ChatTabsFragment fragment = new ChatTabsFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("XXY_CHAT", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_chat_tabs, container, false);
        ButterKnife.bind(this, view);
        String[] titles = new String[]{"History", "Contact"};
        //Have to do it in this way
        //otherwise, when backkey is clicked in chatFragment,
        //creating new sub framgents using new instance do not go through life cycle
        if (chatRoomFragment == null) {
            chatRoomFragment = ChatRoomFragment.newInstance();
        } else {
            FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
            fragTransaction.detach(chatRoomFragment);
            fragTransaction.attach(chatRoomFragment);
            fragTransaction.commit();
        }
        if (chatHistoryFragment == null) {
            chatHistoryFragment = ChatHistoryFragment.newInstance();
        } else {
            FragmentTransaction fragTransaction = getFragmentManager().beginTransaction();
            fragTransaction.detach(chatHistoryFragment);
            fragTransaction.attach(chatHistoryFragment);
            fragTransaction.commit();
        }
        Fragment[] fragments = new Fragment[]{
                chatRoomFragment, chatHistoryFragment
        };
        vpViewPager.setAdapter(
                new ChatTabAdapter(
                        titles,
                        fragments,
                        getActivity().getSupportFragmentManager()
                )
        );
        tabStrip.setViewPager(vpViewPager);


        // Hide toolbar shadow
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) getActivity()
                .findViewById(android.R.id.content)).getChildAt(0);
        View shadow = viewGroup.findViewById(R.id.toolbar_shadow);
        shadow.setVisibility(View.INVISIBLE);

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle("Chat Room");
    }

    @Override
    public void onPause() {
        super.onPause();
        // Show toolbar shadow
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) getActivity()
                .findViewById(android.R.id.content)).getChildAt(0)
        View shadow = viewGroup.findViewById(R.id.toolbar_shadow);
        shadow.setVisibility(View.VISIBLE);
    }

}
