package com.example.customview.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtils {

    public static String fileName = "cacheFile";
    private static SharedPreferences sp;

    public static void initSp(Context context) {
        sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
    }

    public static String getString(Context context, String key) {
        return sp.getString(key, "");
    }

    public static void putString(Context context, String key, String value) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putString(key, value);
        edit.apply();
    }

    public static long getLong(Context context, String title) {
        return sp.getLong(title, 0);
    }

    public static void putLong(Context context, String title, long content) {
        SharedPreferences.Editor edit = sp.edit();
        edit.putLong(title, content);
        edit.apply();
    }

    public static int getInt(Context context,String title){
        return sp.getInt(title,0);
    }

    public static void putInt(Context context,String title,int content){
        SharedPreferences.Editor edit = sp.edit();
        edit.putInt(title,content);
        edit.apply();
    }

}
