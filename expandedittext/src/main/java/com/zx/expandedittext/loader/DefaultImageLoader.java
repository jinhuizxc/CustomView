package com.zx.expandedittext.loader;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.zx.expandedittext.R;

/**
 * 默认的图片加载类
 */
public class DefaultImageLoader extends ImageLoader{

    public DefaultImageLoader(Context context) {
        super(context);
    }

    @Override
    public View getView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.layout_imageview, null, false);
        return view;
    }

    @Override
    public ImageView getImageView(View view) {
        return view.findViewById(R.id.id_expand_imageview);
    }

    @Override
    public void setImageView(ImageView view, String replace, int width) {

    }
}
