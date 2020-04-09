package com.zx.expandedittext.utils;

import android.content.Context;
import android.view.View;

public class DimensUtils {

    /**
     * 获取view的宽度
     * @param view
     * @return
     */
    public static int getViewWidth(View view) {
        view.measure(0, 0);
        int width = view.getMeasuredWidth();
        return width;
    }

    /**
     * 获取view的高度
     * @param view
     * @return
     */
    public static int getViewHeight(View view) {
        view.measure(0, 0);
        int height = view.getMeasuredHeight();
        return height;
    }

    public static int getWidthInPx(Context context) {
        int width = context.getResources().getDisplayMetrics().widthPixels;
        return width;
    }
}
