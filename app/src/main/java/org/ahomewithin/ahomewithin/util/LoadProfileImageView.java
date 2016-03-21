package org.ahomewithin.ahomewithin.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import org.ahomewithin.ahomewithin.R;
import org.ahomewithin.ahomewithin.parseModel.ParseObjectUser;

import java.math.BigInteger;
import java.security.MessageDigest;

/**
 * Created by xiangyang_xiao on 2/7/16.
 */
public class LoadProfileImageView {

    public static void loadProfile(
        final ImageView ivUserProfile, final Context context, ParseObjectUser user) {
        int size = 50;
        Glide.with(context)
            .load(getProfileUrl(user))
            .asBitmap()
            .placeholder(R.drawable.profile_placeholder)
            .error(R.drawable.profile_placeholder)
            .centerCrop()
            .override(size, size)
            .into(new BitmapImageViewTarget(ivUserProfile) {
                @Override
                protected void setResource(Bitmap resource) {
                    RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                    circularBitmapDrawable.setCircular(true);
                    ivUserProfile.setImageDrawable(circularBitmapDrawable);
                }
            });
    }

    // Create a gravatar image based on the hash value obtained from userId
    private static String getProfileUrl(ParseObjectUser mUser) {
        String profileUrl = mUser.getProfile();
        if (profileUrl != null && !TextUtils.isEmpty(profileUrl)) {
            return profileUrl;
        }
        String hex = "";
        try {
            final MessageDigest digest = MessageDigest.getInstance("MD5");
            final byte[] hash = digest.digest(mUser.getEmail().getBytes());
            final BigInteger bigInt = new BigInteger(hash);
            hex = bigInt.abs().toString(16);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "http://www.gravatar.com/avatar/" + hex + "?d=identicon";
    }

}
