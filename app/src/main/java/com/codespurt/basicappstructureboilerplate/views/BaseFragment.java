package com.codespurt.basicappstructureboilerplate.views;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codespurt.basicappstructureboilerplate.engine.callbacks.PermissionCallback;
import com.codespurt.basicappstructureboilerplate.utils.Alerts;
import com.codespurt.basicappstructureboilerplate.utils.Download;
import com.codespurt.basicappstructureboilerplate.utils.Permissions;
import com.codespurt.basicappstructureboilerplate.utils.Preferences;

import java.io.File;
import java.util.Locale;

/**
 * Created by CodeSpurt on 21-09-2017.
 */

public abstract class BaseFragment extends Fragment {

    private Context context;

    protected static boolean isExternalStorageAvailable = false;

    protected Alerts alerts;
    protected Download download;
    protected Permissions permissions;
    protected Preferences preferences;

    protected View v;

    protected final int LAYOUT_MANAGER_LINEAR = 0;
    protected final int LAYOUT_MANAGER_GRID = 1;
    protected final int LAYOUT_MANAGER_STAGGERED = 2;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = getContextForFragment();
        preferences = new Preferences(context);

        setLanguageFromPreference();

        v = inflater.inflate(getLayoutId(), container, false);
        subOnCreateView();
        return v;
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
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadDefaults();
    }

    @Override
    public void onResume() {
        super.onResume();
        enableClicks();
    }

    @Override
    public void onPause() {
        super.onPause();
        disableClicks();
    }

    private void loadDefaults() {
        alerts = new Alerts(context);
        download = new Download(context);
        permissions = new Permissions(getContext(), (Activity) context);

        setFontsToViews();

        // check for Internet connection
        checkInternetAvailability();

        // check permissions
        if (isPermissionsRequired()) {
            if (getPermissionCode() != 0) {
                permissions.checkPermission(getPermissionCode());
            }
        }
    }

    protected View getCurrentView() {
        return v;
    }

    protected boolean checkInternetAvailability() {
        if (!alerts.isNetworkAvailable()) {
            return false;
        } else {
            return true;
        }
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

    private void createDirectoryInStorage() {
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
    protected abstract Context getContextForFragment();

    protected abstract int getLayoutId();

    protected abstract void subOnCreateView();

    protected abstract void setFontsToViews();

    // permissions
    protected abstract boolean isPermissionsRequired();

    protected abstract int getPermissionCode();

    protected abstract PermissionCallback getPermissionCallback();

    // listeners
    protected abstract void enableClicks();

    protected abstract void disableClicks();
}