package org.ahomewithin.ahomewithin.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.parseModel.ParseMessage;
import org.ahomewithin.ahomewithin.parseModel.ParseObjectUser;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.List;

/**
 * Created by xiangyang_xiao on 2/17/16.
 */
public class ChatListAdapter extends ArrayAdapter<ParseMessage> {
    private ParseObjectUser mUser;

    public ChatListAdapter(Context context, ParseObjectUser user, List<ParseMessage> messages) {
        super(context, 0, messages);
        this.mUser = user;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).
                inflate(R.layout.chat_item, parent, false);
            final ViewHolder holder = new ViewHolder();
            holder.imageOther = (ImageView) convertView.findViewById(R.id.ivProfileOther);
            holder.imageMe = (ImageView) convertView.findViewById(R.id.ivProfileMe);
            holder.body = (TextView) convertView.findViewById(R.id.tvBody);
            convertView.setTag(holder);
        }
        final ParseMessage message = getItem(position);
        final ViewHolder holder = (ViewHolder) convertView.getTag();
        String messageId = message.getUserId();

        final boolean isMe = messageId.equals(mUser.getObjectId());
        // Show-hide image based on the logged-in user.
        // Display the profile image to the right for our user, left for other users.
        if (isMe) {
            holder.imageMe.setVisibility(View.VISIBLE);
            holder.imageOther.setVisibility(View.GONE);
            holder.body.setGravity(Gravity.CENTER_VERTICAL | Gravity.RIGHT);
        } else {
            holder.imageOther.setVisibility(View.VISIBLE);
            holder.imageMe.setVisibility(View.GONE);
            holder.body.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);
        }
        final ImageView profileView = isMe ? holder.imageMe : holder.imageOther;
        Glide.with(getContext())
            .load(getProfileUrl())
            .placeholder(R.drawable.profile_placeholder)
            .error(R.drawable.profile_placeholder)
            .into(profileView);
        holder.body.setText(message.getBody());
        return convertView;
    }

    // Create a gravatar image based on the hash value obtained from userId
    private String getProfileUrl() {
        String profileUrl = mUser.getProfile();
        if (profileUrl != null && !TextUtils.isEmpty(profileUrl)) {
            return profileUrl;
        }
        String hex = "";
        try {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            final byte[] hash = digest.digest(mUser.getObjectId().getBytes());
            final BigInteger bigInt = new BigInteger(hash);
            hex = bigInt.abs().toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "http://www.gravatar.com/avatar/" + hex + "?d=identicon";
    }

    final class ViewHolder {
        public ImageView imageOther;
        public ImageView imageMe;
        public TextView body;
    }
}
