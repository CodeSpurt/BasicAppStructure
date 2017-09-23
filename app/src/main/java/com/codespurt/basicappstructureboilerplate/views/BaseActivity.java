package com.codespurt.basicappstructureboilerplate.views;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.codespurt.basicappstructureboilerplate.R;
import com.codespurt.basicappstructureboilerplate.engine.callbacks.PermissionCallback;
import com.codespurt.basicappstructureboilerplate.utils.Alerts;
import com.codespurt.basicappstructureboilerplate.utils.Download;
import com.codespurt.basicappstructureboilerplate.utils.Permissions;
import com.codespurt.basicappstructureboilerplate.utils.Preferences;
import com.google.firebase.iid.FirebaseInstanceId;

import java.io.File;
import java.util.Locale;

public abstract class BaseActivity extends AppCompatActivity {

    private String TAG = "FCMDeviceToken";

    protected static boolean isExternalStorageAvailable = false;

    private Context context;

    protected Alerts alerts;
    protected Download download;
    protected Permissions permissions;
    protected Preferences preferences;

    private Toolbar toolbar;

    protected int currentFragmentCount = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = getContext();
        preferences = new Preferences(context);

        setLanguageFromPreference();
        setContentView(getLayoutId());

        // for vector resources
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        loadDefaults();

        // createDirectoryInStorage();
    }

    private void setLanguageFromPreference() {
        String languageToLoad = preferences.get(Preferences.SELECTED_LANGUAGE);
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getContext().getResources().updateConfiguration(config, getContext().getResources().getDisplayMetrics());
    }

    @Override
    protected void onResume() {
        super.onResume();
        enableClicks();
    }

    @Override
    protected void onPause() {
        super.onPause();
        disableClicks();
    }

    private void loadDefaults() {
        alerts = new Alerts(context);
        download = new Download(context);
        permissions = new Permissions(context, (Activity) context);

        subOnCreate();

        // check for Internet connection
        checkInternetAvailability();
        // checkForInternetConnectionRepeatedly();

        setFontsToViews();

        // check for Google Play Services
        if (alerts.isGooglePlayServicesAvailable(this)) {
            // get device token for notifications
            String token = FirebaseInstanceId.getInstance().getToken();
            Log.d(TAG, "Device Token: " + token);

            if (token != null) {
                if (!token.trim().equals("")) {
                    // send token to server

                }
            }
        } else {
            alerts.showToast(R.string.notifications_unavailable);
        }

        // check permissions
        if (isPermissionsRequired()) {
            if (getPermissionCode() != 0) {
                permissions.checkPermission(getPermissionCode());
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (showOptionsMenu()) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_items, menu);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String english = "en";
        String arabic = "ar";
        if (showOptionsMenu()) {
            switch (item.getItemId()) {
                case R.id.english:
                    preferences.save(Preferences.SELECTED_LANGUAGE, english);
                    setUpdatedLanguage();
                    return true;
                case R.id.spanish:
                    preferences.save(Preferences.SELECTED_LANGUAGE, arabic);
                    setUpdatedLanguage();
                    return true;
            }
        }
        return false;
    }

    private void setUpdatedLanguage() {
        String languageToLoad = preferences.get(Preferences.SELECTED_LANGUAGE);
        Locale locale = new Locale(languageToLoad);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getContext().getResources().updateConfiguration(config, getContext().getResources().getDisplayMetrics());

//        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
//            config.setLayoutDirection(config.locale);
//        }
//        context.getResources().updateConfiguration(config, metrics);

        Intent intent = new Intent(context, context.getClass());
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if (disableBackPress()) {
            if (hasFragments()) {
                // fragment
                if (currentFragmentCount != 0) {
                    customBackPress();
                } else {
                    super.onBackPressed();
                }
            } else {
                // activity
                alerts.showToast(R.string.disable_back_press);
            }
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    protected void setToolbar(int title) {
        if (toolbar == null)
            toolbar = (Toolbar) findViewById(R.id.base_toolbar);
        if (toolbar != null) {
            if (hideToolbar()) {
                toolbar.setVisibility(View.GONE);
            } else {
                setSupportActionBar(toolbar);
                getSupportActionBar().setTitle(title);
                if (showToolbarBackArrow()) {
                    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                    getSupportActionBar().setDisplayShowHomeEnabled(true);
                }
            }
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        if (showToolbarBackArrow()) {
            onBackPressed();
            return true;
        } else {
            return super.onSupportNavigateUp();
        }
    }

    protected boolean checkInternetAvailability() {
        if (!alerts.isNetworkAvailable()) {
            alerts.showSnackbarWithAction(getLayout(), R.string.internet_unavailable, Alerts.OPEN_WIFI);
            return false;
        } else {
            return true;
        }
    }

    private void checkForInternetConnectionRepeatedly() {
        final int REPEAT_AFTER = 1; // in minutes
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                checkInternetAvailability();
                handler.postDelayed(this, REPEAT_AFTER * 60 * 1000);
            }
        }, REPEAT_AFTER * 60 * 1000);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        handlePermissionResponse(requestCode, grantResults);
    }

    private void handlePermissionResponse(int requestCode, int[] grantResults) {
        if (getPermissionCallback() != null) {
            permissions.checkPermissionGranted(requestCode, grantResults, getPermissionCallback());
        }
    }

    protected void createDirectoryInStorage() {
        boolean sdCardAvailability = android.os.Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
        if (sdCardAvailability) {
            String directoryName = context.getPackageName();
            String directoryPath = Environment.getExternalStorageDirectory().getPath().toString() + "/Android/data/" + directoryName + "/Images";

            // storage
            File f = new File(directoryPath);
            if (!f.exists()) {
                if (!f.mkdirs()) {
                    alerts.showToast("Error creating External Directory");
                    isExternalStorageAvailable = false;
                }
                isExternalStorageAvailable = true;
            }
        } else {
            alerts.showToast("External Directory not created");
            isExternalStorageAvailable = false;
        }
    }

    // views
    protected abstract Context getContext();

    protected abstract int getLayoutId();

    protected abstract void subOnCreate();

    protected abstract CoordinatorLayout getLayout();

    protected abstract void setFontsToViews();

    protected abstract boolean hasFragments();

    // toolbar
    protected abstract boolean hideToolbar();

    protected abstract boolean showToolbarBackArrow();

    protected abstract boolean disableBackPress();

    protected abstract void customBackPress();

    protected abstract boolean showOptionsMenu();

    // permissions
    protected abstract boolean isPermissionsRequired();

    protected abstract int getPermissionCode();

    protected abstract PermissionCallback getPermissionCallback();

    // listeners
    protected abstract void enableClicks();

    protected abstract void disableClicks();
}