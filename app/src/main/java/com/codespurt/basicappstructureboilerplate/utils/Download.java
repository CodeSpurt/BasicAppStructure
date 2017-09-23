package com.codespurt.basicappstructureboilerplate.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.codespurt.basicappstructureboilerplate.R;
import com.codespurt.basicappstructureboilerplate.views.custom.glideTransformations.RoundedCornersTransformation;

public class Download {

    private Context context;
    private int IMAGE_CORNERS = 70;

    public Download(Context context) {
        this.context = context;
    }

    public void downloadAndSetImage(String url, ImageView imageView) {
        Glide.with(context).load(url)
                .thumbnail(Glide.with(context).load(R.drawable.loading_gif))
                .bitmapTransform(new RoundedCornersTransformation(context, IMAGE_CORNERS, 0))
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView);
    }
}