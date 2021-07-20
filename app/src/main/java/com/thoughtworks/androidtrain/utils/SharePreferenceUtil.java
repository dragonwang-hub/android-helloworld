package com.thoughtworks.androidtrain.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharePreferenceUtil {

    static public void writeBoolean(Activity activity, String key, boolean value) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    static public boolean readBoolean(Activity activity, String key) {
        SharedPreferences sharedPref = activity.getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getBoolean(key, false);
    }
}
