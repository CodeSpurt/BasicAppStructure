package com.codespurt.basicappstructureboilerplate.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.codespurt.basicappstructureboilerplate.R;

public class Preferences {

    private Context context;
    private String fileName;
    private static SharedPreferences sharedPref;

    // params to be saved
    public static String SELECTED_LANGUAGE = "selected_language";
    public static String IS_LOGGED_IN = "is_logged_in";
    public static String USER_ID = "user_id";

    // boolean
    public static String TRUE = "true";
    public static String FALSE = "false";

    public Preferences() {

    }

    public Preferences(Context context) {
        this.context = context;
        fileName = context.getResources().getString(R.string.app_name);
        sharedPref = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    public void save(String key, String value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public void save(String key, int value) {
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, String.valueOf(value));
        editor.commit();
    }

    public String get(String key) {
        String defaultValue = "";
        return sharedPref.getString(key, defaultValue);
    }
}