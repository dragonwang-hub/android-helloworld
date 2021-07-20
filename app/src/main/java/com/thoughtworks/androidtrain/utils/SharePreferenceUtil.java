package com.thoughtworks.androidtrain.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

@SuppressLint("WrongConstant")
public class SharePreferenceUtil {

    static public void writeBoolean(Activity activity, String key, boolean value) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    static public boolean readBoolean(Activity activity, String key, boolean defaultValue) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity.getApplicationContext());
        return sharedPref.getBoolean(key, defaultValue);
    }
}
