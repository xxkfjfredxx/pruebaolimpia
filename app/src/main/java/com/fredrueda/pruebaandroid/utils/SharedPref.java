package com.fredrueda.pruebaandroid.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SharedPref {

    public static void saveData(Context context, String key, String value) {
        SharedPreferences.Editor sha = PreferenceManager
                .getDefaultSharedPreferences(context).edit();
        sha.putString(key, value);
        sha.commit();
    }

    public static String getData(Context context, String key) {
        if (context != null) {
            SharedPreferences sha = PreferenceManager
                    .getDefaultSharedPreferences(context);
            return sha.getString(key, "");
        }else {
            return "";
        }
    }
}
