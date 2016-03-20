package org.ahomewithin.ahomewithin.fragments;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.parse.ParseUser;

import org.ahomewithin.ahomewithin.ParseClient;
import org.ahomewithin.ahomewithin.ParseClientAsyncHandler;
import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.adapters.ChatListAdapter;
import org.ahomewithin.ahomewithin.parseModel.ParseMessage;
import org.ahomewithin.ahomewithin.parseModel.ParseObjectUser;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xiangyang_xiao on 3/19/16.
 */
public class ChatFragment extends Fragment {

    @Bind(R.id.etMessage)
    EditText etParseMessage;
    @Bind(R.id.btSend)
    Button btSend;
    @Bind(R.id.lvChat)
    ListView lvChat;

    static final int POLL_INTERVAL = 100; // milliseconds

    public static ChatFragment newIntance(String otherEmail) {
        Bundle args = new Bundle();
        args.putString("otherEmail", otherEmail);
        ChatFragment chatFragment = new ChatFragment();
        chatFragment.setArguments(args);
        return chatFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.i("xxyChat", "onCreateView");
        View chatView = inflater.inflate(R.layout.fragment_chat, container, false);
        ButterKnife.bind(this, chatView);
        return chatView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getView().setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (event.getAction() == KeyEvent.ACTION_UP || keyCode == KeyEvent.KEYCODE_BACK) {
                    getActivity()
                        .getSupportFragmentManager()
                        .popBackStack();
                    getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction()
                        .detach(ChatFragment.this)
                        .commit();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        Log.i("xxyChat", "onViewCreated");

        final ParseClient client = ParseClient.newInstance(getContext());
        final Handler mHandler = new Handler();  // android.os.Handler
        client.setMessageRunnableHandler(mHandler);

        final List<ParseMessage> mParseMessages = new ArrayList<>();
        final List<ChatListAdapter> mAdapterWrapper = new ArrayList<>();

        final List<Boolean> firstLoadWrapper = new ArrayList<>();
        firstLoadWrapper.add(true);
        final String otherEmail = getArguments().getString("otherEmail");
        final Runnable mRefreshParseMessagesRunnable = new Runnable() {
            @Override
            public void run() {
                refreshParseMessages(
                    client,
                    mParseMessages,
                    otherEmail,
                    mAdapterWrapper.get(0),
                    firstLoadWrapper
                );
                mHandler.postDelayed(this, POLL_INTERVAL);
            }
        };

        client.getParseObjectUserFromEmail(otherEmail, new ParseClientAsyncHandler() {
            @Override
            public void onSuccess(Object obj) {
                ParseObjectUser otherUser = (ParseObjectUser) obj;
                if (ParseUser.getCurrentUser() != null) {
                    ChatListAdapter mAdapter = new ChatListAdapter(
                        getContext(),
                        client.getCurParseObjectUser(),
                        otherUser,
                        mParseMessages
                    );
                    mAdapterWrapper.add(mAdapter);
                    lvChat.setTranscriptMode(1);
                    lvChat.setAdapter(mAdapter);
                    btSend.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String data = etParseMessage.getText().toString();
                            client.sentMessage(data, otherEmail, new ParseClientAsyncHandler() {
                                    @Override
                                    public void onSuccess(Object obj) {
                                        Toast.makeText(
                                            getContext(),
                                            "Succees",
                                            Toast.LENGTH_SHORT
                                        ).show();
                                    }

                                    @Override
                                    public void onFailure(String error) {

                                    }
                                }
                            );
                            etParseMessage.setText(null);
                        }
                    });
                    mHandler.postDelayed(mRefreshParseMessagesRunnable, 0);
                }
            }

            @Override
            public void onFailure(String error) {
                Log.e("ParseClient", "failed to get the other user");

            }
        });
    }

    void refreshParseMessages(
        ParseClient client,
        final List<ParseMessage> mParseMessages,
        String otherEmail,
        final ChatListAdapter mAdapter,
        final List<Boolean> firstLoadWrapper) {
        client.getPastMessages(otherEmail, new ParseClientAsyncHandler() {
            @Override
            public void onSuccess(Object obj) {
                List<ParseMessage> parseMessages = (List<ParseMessage>) obj;
                if (parseMessages.size() > mParseMessages.size()) {
                    mParseMessages.clear();
                    mParseMessages.addAll(parseMessages);
                    mAdapter.notifyDataSetChanged(); // update adapter
                    // Scroll to the bottom of the list on initial load
                    if (firstLoadWrapper.get(0)) {
                        lvChat.setSelection(mAdapter.getCount() - 1);
                        firstLoadWrapper.set(0, false);
                    }
                }

            }

            @Override
            public void onFailure(String error) {
                Log.e("message", error);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.i("xxyChat", "onDestroyView");
        ParseClient.newInstance(getActivity()).stopHanlder();
    }
}
