package com.codespurt.basicappstructureboilerplate.engine.callbacks;

import android.graphics.Bitmap;

public interface ConversionsCallback {

    void getBitmap(Bitmap imageBitmap);

    void getString(String imageString);
}