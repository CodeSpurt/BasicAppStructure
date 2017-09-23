package com.codespurt.basicappstructureboilerplate.utils;

import android.util.Log;

/**
 * Created by CodeSpurt on 22-09-2017.
 */

public class Logger {

    public static final boolean debug = true;

    public static void d(String tag, String message) {
        Log.d(tag, message);
    }
}
