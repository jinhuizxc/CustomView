package com.example.customview.expand_edittext.tools;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.customview.R;

public class ActivityTools {

    /**
     * 使用默认的入场动画效果
     * 从context跳转到target页面
     *
     * @param context
     * @param cls
     */
    public static void toActivity(Activity context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.slide2_in, R.anim.slide2_out);
    }

    public static void toBackActivityAnim(Activity context, Class<?> cls) {
        Intent intent = new Intent(context, cls);
        context.startActivity(intent);
        context.overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }
}
