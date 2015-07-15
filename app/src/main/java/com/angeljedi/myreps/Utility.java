package com.angeljedi.myreps;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Utility {

    /**
     * A method that returns the stored zip code of the user.
     * @param context the context used to get the zip_key string
     * @return the stored zip code
     */
    public static String getZipCode(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getString(context.getString(R.string.pref_zip_key),
                context.getString(R.string.pref_zip_default));
    }

    public static Boolean isEmpty(String string) {
        if (string == null || string.equals("")) {
            return true;
        }
        return false;
    }
}
