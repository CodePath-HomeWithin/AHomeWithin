package org.ahomewithin.ahomewithin.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by chezlui on 16/03/16.
 */
public class UserTools {

    // Save in sharedPreferences
    public static void purchaseItem(Context context, String id) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = sp.edit();

        editor.putBoolean(id, true);
        editor.commit();
    }

    public static boolean isItemPurchased(Context context, String id) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);

        if (sp.contains(id)) {
            return (sp.getBoolean(id, false));
        }
        return false;
    }
}
