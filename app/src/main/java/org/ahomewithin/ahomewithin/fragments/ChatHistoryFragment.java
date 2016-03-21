package org.ahomewithin.ahomewithin.fragments;

import org.ahomewithin.ahomewithin.ParseClient;
import org.ahomewithin.ahomewithin.ParseClientAsyncHandler;
import org.ahomewithin.ahomewithin.R;

/**
 * Created by xiangyang_xiao on 3/20/16.
 */
public class ChatHistoryFragment extends ChatBaseFragment {

    public final static int FRAGMENT_CODE = 9;

    public static ChatHistoryFragment newInstance() {
        ChatHistoryFragment fragment = new ChatHistoryFragment();
        return fragment;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_chat_history;
    }

    @Override
    public int getFragmentCode() {
        return FRAGMENT_CODE;
    }

    @Override
    public void populateUsers(ParseClientAsyncHandler handler) {
        ParseClient client = ParseClient.newInstance(getActivity());
        client.getChatHistory(handler);
    }
}
