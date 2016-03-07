package org.ahomewithin.ahomewithin.user;

import android.content.DialogInterface;

import java.io.Serializable;

/**
 * Created by xiangyang_xiao on 3/6/16.
 */
public interface OnCreateUserListener extends Serializable {
  void onCreateUserListener(DialogInterface dialog, User user, String password);
}
