package com.example.expandedittext.entity;

import android.widget.ImageView;

public class ImageEntity extends BaseEntity {

    private String path;  // 图片路径

    private ImageView imageView;

    public ImageEntity(String path, ImageView imageView) {
        this.path = path;
        this.imageView = imageView;
    }

    public ImageEntity(ImageView imageView) {
        this.imageView = imageView;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    public int getType() {
        return EntityType.TYPE_IMAGE;
    }

    @Override
    public String getText() {
        return path;
    }

    @Override
    public void setText(String text) {
        super.setText(text);
        this.setText(text);
    }
}
