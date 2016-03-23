package org.ahomewithin.ahomewithin.adapters;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;

import org.ahomewithin.ahomewithin.ParseClient;
import org.ahomewithin.ahomewithin.ParseClientAsyncHandler;
import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.fragments.ChatHistoryFragment;
import org.ahomewithin.ahomewithin.fragments.ChatRoomFragment;
import org.ahomewithin.ahomewithin.parseModel.ParseMessage;
import org.ahomewithin.ahomewithin.parseModel.ParseObjectUser;
import org.ahomewithin.ahomewithin.util.LoadProfileImageView;
import org.ahomewithin.ahomewithin.util.OnItemClickListener;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by xiangyang_xiao on 2/28/16.
 */
public class ChatUsersRecyclerViewAdapter
        extends RecyclerView.Adapter<ChatUsersRecyclerViewAdapter.ChatUserViewHolder> {

    private List<ParseObjectUser> mUsers;
    private OnItemClickListener mListener;
    private int mFragmentCode;

    public ChatUsersRecyclerViewAdapter(
            List<ParseObjectUser> users,
            OnItemClickListener listener,
            int fragmentCode

    ) {
        mUsers = users;
        mListener = listener;
        mFragmentCode = fragmentCode;
    }

    @Override
    public ChatUserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View chatUserView = inflater.inflate(R.layout.item_chat_user, parent, false);
        return new ChatUserViewHolder(chatUserView, parent.getContext());
    }

    @Override
    public void onBindViewHolder(ChatUserViewHolder holder, int position) {
        ParseObjectUser user = mUsers.get(position);
        holder.bindView(user);
    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    class ChatUserViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ivProfileImage)
        ImageView ivProfileImage;
        @Bind(R.id.tvUserName)
        TextView tvUserName;
        @Bind(R.id.tvDescription)
        TextView tvDescription;
        @Bind(R.id.ivUserType)
        ImageView ivUserType;

        Context mContext;

        public ChatUserViewHolder(View itemView, Context context) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = context;
            itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (mListener != null) {
                                mListener.onItemClick(v, getLayoutPosition());
                            }
                        }
                    }
            );
        }

        public void bindView(Object userObj) {
            ParseObjectUser user = (ParseObjectUser) userObj;
            LoadProfileImageView.loadProfile(
                    ivProfileImage,
                    mContext,
                    user
            );
            tvUserName.setText(user.getName());
            switch (mFragmentCode) {
                case ChatRoomFragment.FRAGMENT_CODE:
                    if (user.getDesp() == null ||
                            user.getDesp().equals("null")
                            ) {
                        tvDescription.setVisibility(View.INVISIBLE);
                    } else {
                        tvDescription.setText(user.getDesp());
                    }
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        ivUserType.setImageDrawable(mContext.getDrawable(getDrawableUserType(
                                user.getType().toString())));
                        tintDrawableUserType(user.getType().toString(), ivUserType);
                    }
                    break;
                case ChatHistoryFragment.FRAGMENT_CODE:
                    ParseClient client = ParseClient.newInstance(mContext);
                    client.getLastMessageWithUser(
                            user,
                            new ParseClientAsyncHandler() {
                                @Override
                                public void onSuccess(Object obj) {
                                    List<ParseMessage> messages = (List<ParseMessage>) obj;
                                    ParseMessage lastMessage = messages.get(0);
                                    lastMessage.fetchIfNeededInBackground(
                                            new GetCallback<ParseObject>() {
                                                @Override
                                                public void done(ParseObject object, ParseException e) {
                                                    if (e == null) {
                                                        tvDescription.setText(
                                                                ((ParseMessage) object).getBody()
                                                        );
                                                    } else {
                                                        tvDescription.setVisibility(View.INVISIBLE);
                                                    }

                                                }
                                            }
                                    );
                                }

                                @Override
                                public void onFailure(String error) {
                                    tvDescription.setVisibility(View.INVISIBLE);
                                }
                            }
                    );
                    ivUserType.setVisibility(View.GONE);
                    break;
                default:
                    Log.e(
                            "ChatUserRecyclerView",
                            String.format(
                                    "fragment code %d unkown",
                                    mFragmentCode
                            )
                    );
            }

        }

        private int getDrawableUserType(String type) {

            if (type == "SERVICE_PROVIDER") {
                return mContext.getResources().getIdentifier(
                        "service_provider", "drawable", mContext.getPackageName());
            }
            return mContext.getResources().getIdentifier(
                    "community", "drawable", mContext.getPackageName());
        }

        private void tintDrawableUserType(String type, ImageView view) {
            if (type == "SERVICE_PROVIDER") {
                view.setColorFilter(Color.parseColor("#009688"));

            } else {
                view.setColorFilter(Color.parseColor("#ef6c00"));
            }
        }
    }


}
