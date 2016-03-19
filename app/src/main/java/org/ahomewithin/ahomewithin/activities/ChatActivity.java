package org.ahomewithin.ahomewithin.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
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

public class ChatActivity extends AppCompatActivity {
    @Bind(R.id.etMessage)
    EditText etParseMessage;
    @Bind(R.id.btSend)
    Button btSend;

    @Bind(R.id.lvChat)
    ListView lvChat;
    ArrayList<ParseMessage> mParseMessages;
    ChatListAdapter mAdapter;
    boolean mFirstLoad;
    ParseClient client;
    ParseObjectUser otherUser;
    String otherEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        client = ParseClient.newInstance(this);

        //with the other users email
        otherEmail = getIntent().getStringExtra("otherEmail");

        client.getParseObjectUserFromEmail(otherEmail, new ParseClientAsyncHandler() {
            @Override
            public void onSuccess(Object obj) {
                otherUser = (ParseObjectUser) obj;
                if (ParseUser.getCurrentUser() != null) {
                    //only able to chat when logged in
                    startWithCurrentUser();
                    mHandler.postDelayed(mRefreshParseMessagesRunnable, POLL_INTERVAL);
                }
            }

            @Override
            public void onFailure(String error) {
                Log.e("ParseClient", "failed to get the other user");

            }
        });
    }

    void startWithCurrentUser() {
        setupParseMessagePosting();
    }

    private void setupParseMessagePosting() {
        mParseMessages = new ArrayList<>();
        lvChat.setTranscriptMode(1);
        mFirstLoad = true;
        mAdapter = new ChatListAdapter(
            ChatActivity.this,
            client.getCurParseObjectUser(),
            otherUser,
            mParseMessages
        );
        lvChat.setAdapter(mAdapter);
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String data = etParseMessage.getText().toString();
                client.sentMessage(data, otherEmail, new ParseClientAsyncHandler() {
                        @Override
                        public void onSuccess(Object obj) {
                            Toast.makeText(getApplicationContext(), "Succees", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onFailure(String error) {

                        }
                    }
                );
                etParseMessage.setText(null);
            }
        });
    }

    void refreshParseMessages() {
        client.getPastMessages(otherEmail, new ParseClientAsyncHandler() {
            @Override
            public void onSuccess(Object obj) {
                List<ParseMessage> parseMessages = (List<ParseMessage>) obj;
                mParseMessages.clear();
                mParseMessages.addAll(parseMessages);
                mAdapter.notifyDataSetChanged(); // update adapter
                // Scroll to the bottom of the list on initial load
                if (mFirstLoad) {
                    lvChat.setSelection(mAdapter.getCount() - 1);
                    mFirstLoad = false;
                }
            }

            @Override
            public void onFailure(String error) {
                Log.e("message", error);
            }
        });
    }

    static final int POLL_INTERVAL = 100; // milliseconds
    Handler mHandler = new Handler();  // android.os.Handler
    Runnable mRefreshParseMessagesRunnable = new Runnable() {
        @Override
        public void run() {
            refreshParseMessages();
            mHandler.postDelayed(this, POLL_INTERVAL);
        }
    };

}
