package com.zx.customview.app;

public class Constants {
    
    public static class Notification{
        public static final String CHANNEL_ID = "CHANNEL_ID";
        public static final String CHANNEL_NAME = "CHANNEL_NAME";
    }

    public static String getFileProviderName() {
        return App.getApp().getPackageName() + ".fileprovider";
    }

}
