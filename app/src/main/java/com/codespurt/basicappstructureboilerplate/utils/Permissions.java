package com.codespurt.basicappstructureboilerplate.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.codespurt.basicappstructureboilerplate.engine.callbacks.PermissionCallback;

public class Permissions {

    // required permissions
    public static final int GPS_PERMISSION_CODE = 1001;
    public static final int STORAGE_PERMISSION_CODE = 1002;
    private final String[] GPS_PERMISSION = {android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION};
    private final String[] STORAGE_PERMISSION = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};

    // provided permissions
    private final int[] PERMISSION_GRANTED = {PackageManager.PERMISSION_GRANTED};
    private Context context;
    private Activity activity;

    public Permissions(Context context, Activity activity) {
        this.context = context;
        this.activity = activity;
    }

    public void checkPermission(int requestCode) {
        switch (requestCode) {
            case GPS_PERMISSION_CODE:
                if (ActivityCompat.checkSelfPermission(context, GPS_PERMISSION[0]) != PERMISSION_GRANTED[0] &&
                        ActivityCompat.checkSelfPermission(context, GPS_PERMISSION[1]) != PERMISSION_GRANTED[0]) {
                    // request GPS permission
                    ActivityCompat.requestPermissions(activity, new String[]{GPS_PERMISSION[0], GPS_PERMISSION[1]}, GPS_PERMISSION_CODE);
                }
                break;
            case STORAGE_PERMISSION_CODE:
                if (ActivityCompat.checkSelfPermission(context, STORAGE_PERMISSION[0]) != PERMISSION_GRANTED[0] &&
                        ActivityCompat.checkSelfPermission(context, STORAGE_PERMISSION[1]) != PERMISSION_GRANTED[0]) {
                    // request STORAGE permission
                    ActivityCompat.requestPermissions(activity, new String[]{STORAGE_PERMISSION[0], STORAGE_PERMISSION[1]}, STORAGE_PERMISSION_CODE);
                }
                break;
        }
    }

    public void checkPermissionGranted(int requestCode, int[] grantResults, PermissionCallback permissionCallback) {
        switch (requestCode) {
            case GPS_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED[0] && grantResults[1] == PERMISSION_GRANTED[0]) {
                    permissionCallback.permissionGranted();
                } else {
                    permissionCallback.permissionRejected();
                }
                break;
            case STORAGE_PERMISSION_CODE:
                if (grantResults.length > 0 && grantResults[0] == PERMISSION_GRANTED[0] && grantResults[1] == PERMISSION_GRANTED[0]) {
                    permissionCallback.permissionGranted();
                } else {
                    permissionCallback.permissionRejected();
                }
                break;
        }
    }
}