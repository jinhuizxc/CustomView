package com.example.expandedittext.loader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

public abstract class ImageLoader {

    private Context context;

    public ImageLoader(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public abstract View getView(LayoutInflater inflater);

    public abstract ImageView getImageView(View view);
    /**
     * 设置图片
     * @param view
     * @param replace  占位符
     * @param width  图片宽度
     */
    public abstract void setImageView(ImageView view, String replace, int width);


}
