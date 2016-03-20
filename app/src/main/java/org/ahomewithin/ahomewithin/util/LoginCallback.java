package org.ahomewithin.ahomewithin.util;

import android.os.Parcelable;

/**
 * Created by xiangyang_xiao on 3/19/16.
 */
public interface LoginCallback {
    void onPostLogin(int requestCode, Parcelable... extra);
}
