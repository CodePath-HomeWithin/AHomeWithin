package org.ahomewithin.ahomewithin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import org.ahomewithin.ahomewithin.ParseClient;
import org.ahomewithin.ahomewithin.ParseClientAsyncHandler;
import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.adapters.ChatUsersRecyclerViewAdapter;
import org.ahomewithin.ahomewithin.models.User;
import org.ahomewithin.ahomewithin.parseModel.ParseObjectUser;
import org.ahomewithin.ahomewithin.util.OnItemClickListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

/**
 * Created by chezlui on 14/03/16.
 */
public class ChatRoomActivity extends AppCompatActivity {

    @Bind(R.id.rvUsers)
    RecyclerView rvUsers;

    ParseClient client;
    ChatUsersRecyclerViewAdapter rcAdapter;
    List<User> users;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatroom);
        ButterKnife.bind(this);

        client = ParseClient.newInstance(this);
        client.getAllUsers(new ParseClientAsyncHandler() {
            @Override
            public void onSuccess(Object obj) {
                users = (ArrayList<User>) obj;

                LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
                rvUsers.setLayoutManager(layoutManager);
                rvUsers.setHasFixedSize(true);
                rcAdapter = new ChatUsersRecyclerViewAdapter(
                    users,
                    new OnItemClickListener() {
                        @Override
                        public void onItemClick(View itemView, int position) {
                            String otherEmail = users.get(position).email;
                            Intent newIntent = new Intent(getApplicationContext(), ChatActivity.class);
                            newIntent.putExtra("otherEmail", otherEmail);
                            startActivity(newIntent);
                        }
                    }
                );
                AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(rcAdapter);
                alphaAdapter.setDuration(1000);
                alphaAdapter.setInterpolator(new OvershootInterpolator());
                alphaAdapter.setFirstOnly(false);
                rvUsers.setAdapter(new ScaleInAnimationAdapter(alphaAdapter));

                ParseObjectUser currentUser = client.getCurParseObjectUser();
                if (currentUser != null) {
                    int curUsrIdx = -1;
                    for (int idx = 0; idx < users.size(); idx++) {
                        if (client.getCurParseObjectUser().getEmail().equals(
                            users.get(idx).email)) {
                            curUsrIdx = idx;
                        }
                    }
                    if (curUsrIdx != -1) {
                        users.remove(curUsrIdx);
                        rcAdapter.notifyItemRemoved(curUsrIdx);
                    }
                }
            }

            @Override
            public void onFailure(String error) {
                Toast.makeText(
                    getApplicationContext(),
                    String.format(
                        "Failed to fetch users due to ",
                        error
                    ),
                    Toast.LENGTH_SHORT
                ).show();
            }
        });
    }

}
