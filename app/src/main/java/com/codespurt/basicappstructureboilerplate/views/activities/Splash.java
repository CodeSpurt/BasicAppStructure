package com.codespurt.basicappstructureboilerplate.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.codespurt.basicappstructureboilerplate.utils.Preferences;

public class Splash extends AppCompatActivity {

    private Preferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        preferences = new Preferences(this);

        Intent intent;
        if (preferences.get(Preferences.IS_LOGGED_IN).equals(Preferences.TRUE)) {
            intent = new Intent(this, Dashboard.class);
        } else {
            intent = new Intent(this, Login.class);
        }
        startActivity(intent);
        finish();
    }
}