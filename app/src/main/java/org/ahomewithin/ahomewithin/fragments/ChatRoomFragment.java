package org.ahomewithin.ahomewithin.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
 * Created by xiangyang_xiao on 3/19/16.
 */
public class ChatRoomFragment extends Fragment {
    public static final String FRAGMENT_TAG = ChatRoomFragment.class.getSimpleName();

    @Bind(R.id.rvUsers)
    RecyclerView rvUsers;

    public static ChatRoomFragment newInstance() {
        return new ChatRoomFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View chatRoomView = inflater.inflate(R.layout.fragment_chatroom, container, false);
        ButterKnife.bind(this, chatRoomView);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle(R.string.chatRoom);
        return chatRoomView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        final ParseClient client = ParseClient.newInstance(getContext());
        client.getAllUsers(new ParseClientAsyncHandler() {
            @Override
            public void onSuccess(Object obj) {
                final List<User> users = (ArrayList<User>) obj;

                LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
                rvUsers.setLayoutManager(layoutManager);
                rvUsers.setHasFixedSize(true);
                ChatUsersRecyclerViewAdapter rcAdapter =
                    new ChatUsersRecyclerViewAdapter(
                        users,
                        new OnItemClickListener() {
                            @Override
                            public void onItemClick(View itemView, int position) {
                                String otherEmail = users.get(position).email;
                                ChatFragment chatFragment = ChatFragment.newIntance(otherEmail);
                                FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                ft.replace(R.id.flContent, chatFragment);
                                ft.addToBackStack(null);
                                ft.commit();
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
                    getContext(),
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
