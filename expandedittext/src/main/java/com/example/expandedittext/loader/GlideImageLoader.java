package com.example.expandedittext.loader;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.expandedittext.R;

/**
 * 3.7.0
 *  Glide.with(getContext()).load(replace)
 *                 .asBitmap()
 *                 .placeholder(R.drawable.ic_holder)
 *                 .into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
 *                     @Override
 *                     public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
 *                         int imageWidth = resource.getWidth();
 *                         int imageHeight = resource.getHeight();
 *                         int height = width * imageHeight / imageWidth;
 *                         ViewGroup.LayoutParams para = imageView.getLayoutParams();
 *                         para.height = height;
 *                         para.width = width;
 *                         imageView.setImageBitmap(resource);
 *                     }
 *                 });
 */
public class GlideImageLoader extends DefaultImageLoader{

    public GlideImageLoader(Context context) {
        super(context);
    }

    @Override
    public void setImageView(ImageView imageView, String replace, int width) {
        Glide.with(getContext()).load(replace)
                .placeholder(R.drawable.ic_holder)
                .into(imageView);
    }
}
