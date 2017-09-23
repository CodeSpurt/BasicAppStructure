package com.codespurt.basicappstructureboilerplate.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;

import com.codespurt.basicappstructureboilerplate.engine.callbacks.ConversionsCallback;

import java.io.ByteArrayOutputStream;

public class Conversions {

    private static Conversions instance = null;
    private ConversionsCallback callback;

    // singleton
    private Conversions() {

    }

    public static Conversions getInstance() {
        if (instance == null) {
            instance = new Conversions();
        }
        return instance;
    }

    public void bitmapToString(Bitmap bitmap, ConversionsCallback callback) {
        this.callback = callback;
        new bitmapToStringAsync().execute(bitmap);
    }

    public void stringToBitMap(String encodedString, ConversionsCallback callback) {
        this.callback = callback;
        new stringToBitMapAsync().execute(encodedString);
    }

    private class bitmapToStringAsync extends AsyncTask<Bitmap, Void, String> {

        @Override
        protected String doInBackground(Bitmap... bitmap) {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap[0].compress(Bitmap.CompressFormat.PNG, 100, baos);
                byte[] bytes = baos.toByteArray();
                return Base64.encodeToString(bytes, Base64.DEFAULT);
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }

        @Override
        protected void onPostExecute(String string) {
            super.onPostExecute(string);
            callback.getString(string);
        }
    }

    private class stringToBitMapAsync extends AsyncTask<String, Void, Bitmap> {

        @Override
        protected Bitmap doInBackground(String... encodedString) {
            try {
                byte[] encodeByte = Base64.decode(encodedString[0], Base64.DEFAULT);
                Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                return bitmap;
            } catch (Exception e) {
                e.getMessage();
                return null;
            }
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            callback.getBitmap(bitmap);
        }
    }
}