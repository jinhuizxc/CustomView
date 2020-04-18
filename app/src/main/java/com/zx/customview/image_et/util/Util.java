package com.zx.customview.image_et.util;

import android.content.Context;
import android.view.WindowManager;

import com.zx.customview.app.App;

public class Util {

    public static final int SCENE_WIDTH = 1;
    public static final int SCENE_HEIGHT = 2;

    public static int getScene(int param) {
        WindowManager wm = (WindowManager) App.getApp().getSystemService(Context.WINDOW_SERVICE);
        if (param == SCENE_WIDTH) {
            return wm.getDefaultDisplay().getWidth();
        } else if (param == SCENE_HEIGHT) {
            return wm.getDefaultDisplay().getHeight();
        }
        return 0;
    }

}
