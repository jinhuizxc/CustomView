package com.example.customview.utils;

import android.content.Context;

public class PixelUtil {

    //实际绘制时，需要使用像素进行绘制，此处提供sp/sp转px的方法
    public static int dp2px(Context context, float dpValue){
        float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


//    private int sp2px(Context context, float spValue) {
//        final float scale = context.getResources().getDisplayMetrics().density;
//        return (int) (spValue * scale + 0.5f);
//    }

}
