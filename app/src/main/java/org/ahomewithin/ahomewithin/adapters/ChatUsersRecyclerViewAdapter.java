package org.ahomewithin.ahomewithin.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.models.User;
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

    private List<User> mUsers;
    private OnItemClickListener mListener;

    public ChatUsersRecyclerViewAdapter(
        List<User> users,
        OnItemClickListener listener
    ) {
        mUsers = users;
        mListener = listener;
    }

    @Override
    public ChatUserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View chatUserView = inflater.inflate(R.layout.item_chat_user, parent, false);
        return new ChatUserViewHolder(chatUserView, parent.getContext());
    }

    @Override
    public void onBindViewHolder(ChatUserViewHolder holder, int position) {
        User user = mUsers.get(position);
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
        @Bind(R.id.tvUserType)
        TextView tvUserType;

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
            User user = (User) userObj;
            LoadProfileImageView.loadProfile(
                ivProfileImage,
                mContext,
                user
            );
            tvUserName.setText(user.lastName);
            if (user.description == null ||
                user.description.equals("null")
                ) {
                tvDescription.setVisibility(View.INVISIBLE);
            } else {
                tvDescription.setText(user.description);
            }
            tvUserType.setText(user.type.toString());
        }
    }

}
