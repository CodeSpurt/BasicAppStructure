package com.codespurt.basicappstructureboilerplate.utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.codespurt.basicappstructureboilerplate.R;
import com.codespurt.basicappstructureboilerplate.engine.callbacks.AlertsCallback;
import com.codespurt.basicappstructureboilerplate.views.custom.CustomTextView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;

public class Alerts {

    private Context context;
    private int toastPositionFromTop = 500;
    private ProgressDialog progressDialog;

    // actions
    public static final int OPEN_WIFI = 0001;
    public static final int NO_ACTION = 0000;

    public Alerts(Context context) {
        this.context = context;
    }

    public boolean isGooglePlayServicesAvailable(Activity activity) {
        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();
        int status = googleApiAvailability.isGooglePlayServicesAvailable(activity);
        if (status != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(status)) {
                googleApiAvailability.getErrorDialog(activity, status, 2404).show();
            }
            return false;
        }
        return true;
    }

    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    public void showToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public void showToast(int message) {
        Toast.makeText(context, context.getResources().getString(message), Toast.LENGTH_LONG).show();
    }

    public void showCustomToast(String message, int imageId) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rootView = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
        View view = inflater.inflate(R.layout.custom_toast, (ViewGroup) rootView.findViewById(R.id.custom_toast_layout));

        ImageView image = (ImageView) view.findViewById(R.id.custom_toast_image);
        image.setImageResource(imageId);
        CustomTextView text = (CustomTextView) view.findViewById(R.id.custom_toast_message);
        text.setText(message);

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.TOP, 0, toastPositionFromTop);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

    public void showCustomToast(int message, int imageId) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rootView = ((Activity) context).getWindow().getDecorView().findViewById(android.R.id.content);
        View view = inflater.inflate(R.layout.custom_toast, (ViewGroup) rootView.findViewById(R.id.custom_toast_layout));

        ImageView image = (ImageView) view.findViewById(R.id.custom_toast_image);
        image.setImageResource(imageId);
        CustomTextView text = (CustomTextView) view.findViewById(R.id.custom_toast_message);
        text.setText(context.getResources().getString(message));

        Toast toast = new Toast(context);
        toast.setGravity(Gravity.TOP, 0, toastPositionFromTop);
        toast.setDuration(Toast.LENGTH_LONG);
        toast.setView(view);
        toast.show();
    }

    public void showSnackbar(View view, String message) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show();
    }

    public void showSnackbar(View view, int message) {
        Snackbar.make(view, context.getResources().getString(message), Snackbar.LENGTH_LONG).show();
    }

    public void showSnackbarWithAction(View view, String message, final int action) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        handleAction(action);
                    }
                });

        snackbar.setActionTextColor(Color.WHITE);

        View snackbarView = snackbar.getView();
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.GRAY);
        snackbar.show();
    }

    public void showSnackbarWithAction(View view, int message, final int action) {
        Snackbar snackbar = Snackbar.make(view, context.getResources().getString(message), Snackbar.LENGTH_INDEFINITE)
                .setAction("OK", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        handleAction(action);
                    }
                });

        snackbar.setActionTextColor(Color.WHITE);

        View snackbarView = snackbar.getView();
        TextView textView = (TextView) snackbarView.findViewById(android.support.design.R.id.snackbar_text);
        textView.setTextColor(Color.GRAY);
        snackbar.show();
    }

    public void showAlertDialog(String title, String message, boolean cancelable, String positiveButton, String negativeButton, final int action) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(cancelable);

        builder.setPositiveButton(
                positiveButton,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        handleAction(action);
                        dialog.dismiss();
                    }
                });

        builder.setNegativeButton(
                negativeButton,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void showAlertDialog(int title, int message, boolean cancelable, String positiveButton, String negativeButton, final int action) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getResources().getString(title));
        builder.setMessage(context.getResources().getString(message));
        builder.setCancelable(cancelable);

        builder.setPositiveButton(
                positiveButton,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        handleAction(action);
                        dialog.dismiss();
                    }
                });

        builder.setNegativeButton(
                negativeButton,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void showAlertCustomDialog(String title, String message, boolean cancelable, int layoutId, String positiveButton, String negativeButton, final int action, final AlertsCallback alertsCallback) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setCancelable(cancelable);

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(layoutId, null);
        final EditText input = (EditText) view.findViewById(R.id.custom_dialog_input);
        builder.setView(view);

        builder.setPositiveButton(
                positiveButton,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        String s = input.getText().toString();
                        alertsCallback.dialogInput(s);
                        dialog.dismiss();
                    }
                });

        builder.setNegativeButton(
                negativeButton,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void handleAction(int action) {
        switch (action) {
            case OPEN_WIFI:
                context.startActivity(new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS));
                break;
            case NO_ACTION:
                break;
        }
    }

    public void showProgressDialog() {
        hideKeyboard();
        String message = "Loading...";
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(message);
            progressDialog.setCancelable(false);
        }
        progressDialog.show();
    }

    public void hideProgressDialog() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

    public void hideKeyboard() {
        Activity activity = (Activity) context;
        View view = activity.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}