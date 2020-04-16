package com.example.expandview;

import android.util.Log;

public class LogUtils {

    private static boolean isLog = true;
    private static final String TAG = "LogUtils";

    public static boolean isIsLog() {
        return isLog;
    }

    public static void setIsLog(boolean isLog) {
        LogUtils.isLog = isLog;
    }

    public static void d(String msg){
        if (isLog){
            Log.d(TAG, msg);
        }
    }
}
