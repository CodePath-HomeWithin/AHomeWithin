package org.ahomewithin.ahomewithin.util;

import android.content.DialogInterface;

import java.io.Serializable;

/**
 * Created by xiangyang_xiao on 3/6/16.
 */
public interface OnResetPasswordListener extends Serializable{
  void onResetPasswordListener(DialogInterface dialog, String email);
}
