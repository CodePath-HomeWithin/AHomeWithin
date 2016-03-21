package org.ahomewithin.ahomewithin.fragments;

import org.ahomewithin.ahomewithin.ParseClient;
import org.ahomewithin.ahomewithin.ParseClientAsyncHandler;
import org.ahomewithin.ahomewithin.R;

/**
 * Created by xiangyang_xiao on 3/19/16.
 */
public class ChatRoomFragment extends Fragment {

    public final static int FRAGMENT_CODE = 1;

    public static ChatRoomFragment newInstance() {
        ChatRoomFragment fragment = new ChatRoomFragment();
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_chat_room;
    }

    @Override
    public void populateUsers(ParseClientAsyncHandler handler) {
        ParseClient client = ParseClient.newInstance(getContext());
        client.getAllUsers(handler);
    }

    @Override
    public int getFragmentCode() {
        return FRAGMENT_CODE;
    }
}
