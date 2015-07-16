package com.angeljedi.myreps;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ShapeDrawable;
import android.os.Build;
import android.preference.PreferenceManager;
import android.view.View;

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

    /**
     * A method to determine if a string is null or empty
     * @param string the string to test
     * @return true if the boolean is null or empty
     */
    public static Boolean isEmpty(String string) {
        if (string == null || string.equals("")) {
            return true;
        }
        return false;
    }

    /**
     * A utility method to return the color value for a given party
     * @param context the context used to look up the color value
     * @param party the party to get the color for
     * @return the id of the color associated with the party
     */
    public static int getPartyColor(Context context, String party) {
        int color = context.getResources().getColor(R.color.party_other);
        if (party.equals("R")) {
            color = context.getResources().getColor(R.color.party_republican);
        } else if (party.equals("D")) {
            color = context.getResources().getColor(R.color.party_democrat);
        }

        return color;
    }

    /**
     * A utility method that sets background color of a ShapeDrawable to a given party color
     * @param view the view containing the ShapeDrawable
     * @param partyColor the color to set the background to
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public static void setPartyColor(View view, int partyColor) {
        ShapeDrawable drawable = new ShapeDrawable();
        drawable.getPaint().setColor(partyColor);
        view.setBackground(drawable);
    }
}
