package org.ahomewithin.ahomewithin.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.models.User;
import org.ahomewithin.ahomewithin.parseModel.ParseMessage;
import org.ahomewithin.ahomewithin.parseModel.ParseObjectUser;
import org.ahomewithin.ahomewithin.util.LoadProfileImageView;

import java.util.List;

/**
 * Created by xiangyang_xiao on 2/17/16.
 */
public class ChatListAdapter extends ArrayAdapter<ParseMessage> {
    private ParseObjectUser curUser;
    private ParseObjectUser otherUser;
    private ParseObjectUser user;

    public ChatListAdapter(
        Context context,
        ParseObjectUser curUser,
        ParseObjectUser otherUser,
        List<ParseMessage> messages) {
        super(context, 0, messages);
        this.curUser = curUser;
        this.otherUser = otherUser;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                inflate(R.layout.item_chat, parent, false);
            final ViewHolder holder = new ViewHolder();
            holder.imageOther = (ImageView) convertView.findViewById(R.id.ivProfileOther);
            holder.imageMe = (ImageView) convertView.findViewById(R.id.ivProfileMe);
            holder.body = (TextView) convertView.findViewById(R.id.tvBody);
            convertView.setTag(holder);
        }
        final ParseMessage message = getItem(position);
        final ViewHolder holder = (ViewHolder) convertView.getTag();
        String messageId = message.getUserId();

        final boolean isMe = messageId.equals(curUser.getObjectId());
        // Show-hide image based on the logged-in user.
        // Display the profile image to the right for our user, left for other users.
        if (isMe) {
            holder.imageMe.setVisibility(View.VISIBLE);
            holder.imageOther.setVisibility(View.GONE);
            holder.body.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
            user = curUser;
        } else {
            holder.imageOther.setVisibility(View.VISIBLE);
            holder.imageMe.setVisibility(View.GONE);
            holder.body.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
            user = otherUser;
        }
        final ImageView profileView = isMe ? holder.imageMe : holder.imageOther;
        LoadProfileImageView.loadProfile(
            profileView,
            getContext(),
            User.getNewInstanceFromParseObject(user)
        );

        holder.body.setText(message.getBody());
        return convertView;
    }

    final class ViewHolder {
        public ImageView imageOther;
        public ImageView imageMe;
        public TextView body;
    }
}
