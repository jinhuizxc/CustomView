package com.zx.expandedittext.listener;

import android.view.View;

import com.zx.expandedittext.entity.ImageEntity;

/**
 * 图片的点击事件
 */
public interface OnExpandImageClickListener {

    public void onImageClick(View view, ImageEntity imageEntity);
}
