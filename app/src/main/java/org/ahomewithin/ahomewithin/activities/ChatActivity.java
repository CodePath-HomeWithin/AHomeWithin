package org.ahomewithin.ahomewithin.activities;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
  static final int MAX_CHAT_MESSAGES_TO_SHOW = 50;

  static final String TAG = ChatActivity.class.getSimpleName();

  EditText etParseMessage;
  Button btSend;

  ListView lvChat;
  ArrayList<ParseMessage> mParseMessages;
  ChatListAdapter mAdapter;
  boolean mFirstLoad;
  ParseClient client;
  String otherEmail;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_chat);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);
    if (ParseUser.getCurrentUser() != null) {
      //only able to chat when logged in
      startWithCurrentUser();
      mHandler.postDelayed(mRefreshParseMessagesRunnable, POLL_INTERVAL);
    }

    client = ParseClient.newInstance(this);
    //TODO : this should be an intent
    //with the other users email
    otherEmail = getIntent().getStringExtra("otherEmail");
  }

  void startWithCurrentUser() {
    setupParseMessagePosting();

  }

  private void setupParseMessagePosting() {
    etParseMessage = (EditText) findViewById(R.id.etMessage);
    btSend = (Button) findViewById(R.id.btSend);
    lvChat = (ListView) findViewById(R.id.lvChat);
    mParseMessages = new ArrayList<>();
    lvChat.setTranscriptMode(1);
    mFirstLoad = true;
    final String userId = ParseUser.getCurrentUser().getObjectId();
    mAdapter = new ChatListAdapter(ChatActivity.this, userId, mParseMessages);
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

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_chat, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
