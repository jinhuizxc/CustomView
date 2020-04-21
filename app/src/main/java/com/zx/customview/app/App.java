package com.zx.customview.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.multidex.MultiDex;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.AndroidLogAdapter;
import com.orhanobut.logger.FormatStrategy;
import com.orhanobut.logger.Logger;
import com.orhanobut.logger.PrettyFormatStrategy;
import com.sendtion.xrichtext.IImageLoader;
import com.sendtion.xrichtext.XRichText;
import com.zx.customview.R;
import com.zx.customview.xrichtext.TransformationScale;

public class App extends Application implements Application.ActivityLifecycleCallbacks {

    private static App instance;

    /*获取当前显示的Activity*/
    private Activity mActivity;

    @Override
    public void onCreate() {
        super.onCreate();

        instance = this;

        registerActivityLifecycleCallbacks(this);

        // 统一logger日志打印
        initLogger();

        // 因为adLibrary需要这个依赖;
        Fresco.initialize(this);

        initRichText();

    }

    private void initRichText() {
        XRichText.getInstance().setImageLoader(new IImageLoader() {
            @Override
            public void loadImage(final String imagePath, final ImageView imageView, final int imageHeight) {
                Log.e("---", "imageHeight: "+imageHeight);
                //如果是网络图片
                if (imagePath.startsWith("http://") || imagePath.startsWith("https://")){
                    Glide.with(getApplicationContext()).asBitmap().load(imagePath).dontAnimate()
                            .into(new SimpleTarget<Bitmap>() {
                                @Override
                                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                                    if (imageHeight > 0) {//固定高度
                                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                                                FrameLayout.LayoutParams.MATCH_PARENT, imageHeight);//固定图片高度，记得设置裁剪剧中
                                        lp.bottomMargin = 10;//图片的底边距
                                        imageView.setLayoutParams(lp);
                                        Glide.with(getApplicationContext()).asBitmap().load(imagePath).centerCrop()
                                                .placeholder(R.mipmap.img_load_fail).error(R.mipmap.img_load_fail).into(imageView);
                                    } else {//自适应高度
                                        Glide.with(getApplicationContext()).asBitmap().load(imagePath)
                                                .placeholder(R.mipmap.img_load_fail).error(R.mipmap.img_load_fail).into(new TransformationScale(imageView));
                                    }
                                }
                            });
                } else { //如果是本地图片
                    if (imageHeight > 0) {//固定高度
                        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                                FrameLayout.LayoutParams.MATCH_PARENT, imageHeight);//固定图片高度，记得设置裁剪剧中
                        lp.bottomMargin = 10;//图片的底边距
                        imageView.setLayoutParams(lp);

                        Glide.with(getApplicationContext()).asBitmap().load(imagePath).centerCrop()
                                .placeholder(R.mipmap.img_load_fail).error(R.mipmap.img_load_fail).into(imageView);
                    } else {//自适应高度
                        Glide.with(getApplicationContext()).asBitmap().load(imagePath)
                                .placeholder(R.mipmap.img_load_fail).error(R.mipmap.img_load_fail).into(new TransformationScale(imageView));
                    }
                }
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    private void initLogger() {
        FormatStrategy formatStrategy = PrettyFormatStrategy.newBuilder()
                .showThreadInfo(false)  // (Optional) Whether to show thread info or not. Default true
                .methodCount(2)         // (Optional) How many method line to show. Default 2
                .methodOffset(5)        // (Optional) Skips some method invokes in stack trace. Default 5
//        .logStrategy(customLog) // (Optional) Changes the log strategy to print out. Default LogCat
                .tag("CustomView")   // (Optional) Custom tag for each log. Default PRETTY_LOGGER
                .build();
        //  logger open or close
        Logger.addLogAdapter(new AndroidLogAdapter(formatStrategy) {
            @Override
            public boolean isLoggable(int priority, String tag) {
                return true;
            }
        });
    }

    public static String getFileProviderName() {
        return App.getApp().getPackageName() + ".fileprovider";
    }


    public static App getApp() {
        return instance;
    }



    /**
     * 显示View
     *
     * @param view 需要显示到Activity的视图
     */
    public void showView(View view) {
        /*Activity不为空并且没有被释放掉*/
        if (this.mActivity != null && !this.mActivity.isFinishing()) {
            /*获取Activity顶层视图,并添 加自定义View*/
            ((ViewGroup) this.mActivity.getWindow().getDecorView()).addView(view);
        }
    }


    /**
     * 隐藏View
     *
     * @param view 需要从Activity中移除的视图
     */
    public void hideView(View view) {
        /*Activity不为空并且没有被释放掉*/
        if (this.mActivity != null && !this.mActivity.isFinishing()) {
            /*获取Activity顶层视图*/
            ViewGroup root = ((ViewGroup) this.mActivity.getWindow().getDecorView());
            /*如果Activity中存在View对象则删除*/
            if (root.indexOfChild(view) != -1) {
                /*从顶层视图中删除*/
                root.removeView(view);
            }
        }
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityResumed(Activity activity) {
        /*获取当前显示的Activity*/
        this.mActivity = activity;
    }

    @Override
    public void onActivityPaused(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}
