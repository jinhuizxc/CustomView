package com.example.expandedittext.listener;

import android.view.View;
import android.widget.ImageView;

import com.example.expandedittext.entity.ImageEntity;

/**
 * 图片的点击事件
 */
public interface OnExpandImageClickListener {

    public void onImageClick(View view, ImageEntity imageEntity);
}
