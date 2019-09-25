package com.example.dotview.utils;

import android.content.Context;

public class DotUtil {

    /**
     * dp转为px
     * @param context
     * @param dp
     * @return
     */
    public static int dp2px(Context context, float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density + 0.5f);
    }
}
