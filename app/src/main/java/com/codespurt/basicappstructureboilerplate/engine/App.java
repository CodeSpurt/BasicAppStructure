package com.codespurt.basicappstructureboilerplate.engine;

import android.app.Application;

import com.codespurt.basicappstructureboilerplate.sync.NetworkManagerVolley;

public class App extends Application {

    public static final String fontName = "EagleLake-Regular";
    public static final String fontButton = "EagleLake-Regular";
    public static final String fontEditText = "EagleLake-Regular";
    public static final String fontTextView = "EagleLake-Regular";
    public static final String fontExtension = ".ttf";

    public static final String AES_KEY = "";

    @Override
    public void onCreate() {
        super.onCreate();

        NetworkManagerVolley.getInstance(this);

//        NetworkManagerRetrofit.getInstance(this);
    }
}