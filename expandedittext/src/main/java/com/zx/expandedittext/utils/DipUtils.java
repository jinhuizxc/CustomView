package com.zx.expandedittext.utils;

import android.content.Context;

public class DipUtils {

    public static int dp2px(Context context, float dp) {
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (scale * dp + 0.5f);
    }
}
