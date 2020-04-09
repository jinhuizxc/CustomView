package com.zx.expandedittext.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;

public class BitmapUtils {

    // 等比缩放图片
    public static Bitmap zoomImg(Bitmap bitmap, int newWidth) {
        // 获得原图片的宽高
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        // 计算宽度比例
        float scale = ((float) newWidth) / width;

        Matrix matrix = new Matrix();
        matrix.postScale(scale, scale);

        Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return newBitmap;
    }

    public static Bitmap zoomAdapter(Bitmap bitmap, int newWidth) {
        // 获得原图片的宽高
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        float scale = -1;
        if (width > (newWidth / 3)) {
            scale = ((float) newWidth) / width;

            Matrix matrix = new Matrix();
            matrix.postScale(scale, scale);

            Bitmap newBitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
            return newBitmap;
        }
        return bitmap;

    }

}
