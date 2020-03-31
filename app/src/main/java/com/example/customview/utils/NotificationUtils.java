package com.example.customview.utils;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RemoteViews;

import com.blankj.utilcode.util.LogUtils;
import com.example.customview.R;
import com.example.customview.app.App;
import com.example.customview.app.Constants;
import com.example.customview.widget.dialog.QuireDialog;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static androidx.core.app.NotificationCompat.PRIORITY_DEFAULT;
import static androidx.core.app.NotificationCompat.VISIBILITY_SECRET;

/**
 * <pre>
 *     @author yangchong
 *     blog  : https://www.jianshu.com/p/514eb6193a06
 *     time  : 2018/2/10
 *     desc  : 通知栏工具类
 *     revise:
 * </pre>
 */
public class NotificationUtils extends ContextWrapper {


    private NotificationManager mManager;
    private int[] flags;

    private static NotificationUtils instance;

    public static NotificationUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (NotificationUtils.class) {
                if (instance == null) {
                    instance = new NotificationUtils(context);
                }
            }
        }
        return instance;
    }

    public NotificationUtils(Context context) {
        super(context);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //android 8.0以上需要特殊处理，也就是targetSDKVersion为26以上
            createNotificationChannel();
        }
    }

    /**
     * 创建View并启动动画
     */
    @SuppressLint("InflateParams")
    public static void createTopMsg(Context context) {
        /*创建提示消息View*/
        final View view = LayoutInflater.from(App.getApp()).inflate(R.layout.view_top_msg, null);
        // 设置view可跳转(计划跳转到指定聊天会话!)
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ToastUtils.showShort("");
            }
        });
        /*创建属性动画,从下到上*/
        ObjectAnimator bottomToTop = ObjectAnimator.ofFloat(view, "translationY", 0, -dp2px(80)).setDuration(500);
        /*创建属性动画,从上到下*/
        ObjectAnimator topToBottom = ObjectAnimator.ofFloat(view, "translationY", -dp2px(80), 0).setDuration(500);
        /*初始化动画组合器*/
        AnimatorSet animator = new AnimatorSet();
        /*组合动画*/
        animator.play(bottomToTop).after(topToBottom).after(2000);
        /*添加动画结束回调*/
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                /*删除View*/
                App.getApp().hideView(view);
            }
        });
        /*添加View到当前显示的Activity*/
        App.getApp().showView(view);
        /*启动动画*/
        animator.start();
    }

    /**
     * 从dp单位转换为px
     *
     * @param dp dp值
     * @return 返回转换后的px值
     */
    public static int dp2px(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }


    @TargetApi(Build.VERSION_CODES.O)
    private void createNotificationChannel() {
        //第一个参数：channel_id
        //第二个参数：channel_name
        //第三个参数：设置通知重要性级别
        //注意：该级别必须要在 NotificationChannel 的构造函数中指定，总共要五个级别；
        //范围是从 NotificationManager.IMPORTANCE_NONE(0) ~ NotificationManager.IMPORTANCE_HIGH(4)
        NotificationChannel channel = new NotificationChannel(Constants.Notification.CHANNEL_ID,
                Constants.Notification.CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT);
        channel.canBypassDnd();//是否绕过请勿打扰模式
        channel.enableLights(true);//闪光灯
        channel.setLockscreenVisibility(VISIBILITY_SECRET);//锁屏显示通知
        channel.setLightColor(Color.RED);//闪关灯的灯光颜色
        channel.canShowBadge();//桌面launcher的消息角标
        channel.enableVibration(true);//是否允许震动
        channel.setShowBadge(true);  // 显示角标
        channel.getAudioAttributes();//获取系统通知响铃声音的配置
        channel.getGroup();//获取通知取到组
        channel.setBypassDnd(true);//设置可绕过 请勿打扰模式
        channel.setVibrationPattern(new long[]{100, 100, 200});//设置震动模式
        channel.shouldShowLights();//是否会有灯光
        getManager().createNotificationChannel(channel);
    }

    /**
     * 获取创建一个NotificationManager的对象
     *
     * @return NotificationManager对象
     */
    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    /**
     * 清空所有的通知
     */
    public void clearNotification() {
        getManager().cancelAll();
    }

    /**
     * 获取Notification
     *
     * @param title   title
     * @param content content
     */
    public Notification getNotification(String title, String content, int icon) {
        Notification build;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //android 8.0以上需要特殊处理，也就是targetSDKVersion为26以上
            //通知用到NotificationCompat()这个V4库中的方法。但是在实际使用时发现书上的代码已经过时并且Android8.0已经不支持这种写法
            Notification.Builder builder = getChannelNotification(title, content, icon);
            build = builder.build();
        } else {
            NotificationCompat.Builder builder = getNotificationCompat(title, content, icon);
            build = builder.build();
        }
        if (flags != null && flags.length > 0) {
            for (int a = 0; a < flags.length; a++) {
                build.flags |= flags[a];
            }
        }
        return build;
    }

    /**
     * 建议使用这个发送通知
     * 调用该方法可以发送通知
     *
     * @param notifyId notifyId
     * @param title    title
     * @param content  content
     */
    public void sendNotification(int notifyId, String title, String content, int icon) {
        Notification build;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //android 8.0以上需要特殊处理，也就是targetSDKVersion为26以上
            //通知用到NotificationCompat()这个V4库中的方法。但是在实际使用时发现书上的代码已经过时并且Android8.0已经不支持这种写法
            Notification.Builder builder = getChannelNotification(title, content, icon);
            build = builder.build();
        } else {
            NotificationCompat.Builder builder = getNotificationCompat(title, content, icon);
            build = builder.build();
        }
        if (flags != null && flags.length > 0) {
            for (int a = 0; a < flags.length; a++) {
                build.flags |= flags[a];
            }
        }
        getManager().notify(notifyId, build);
    }

    /**
     * 调用该方法可以发送通知
     *
     * @param notifyId notifyId
     * @param title    title
     * @param content  content
     */
    public void sendNotificationCompat(int notifyId, String title, String content, int icon) {
        NotificationCompat.Builder builder = getNotificationCompat(title, content, icon);
        Notification build = builder.build();
        if (flags != null && flags.length > 0) {
            for (int a = 0; a < flags.length; a++) {
                build.flags |= flags[a];
            }
        }
        getManager().notify(notifyId, build);
    }


    public static NotificationCompat.Builder getNotificationCompat(String title, String content, int icon) {
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new NotificationCompat.Builder(App.getApp(), Constants.Notification.CHANNEL_ID);
        } else {
            // 注意用下面这个方法，在8.0以上无法出现通知栏。8.0之前是正常的。这里需要增强判断逻辑
            builder = new NotificationCompat.Builder(App.getApp());
            builder.setPriority(PRIORITY_DEFAULT);
        }
        builder.setContentTitle(title);
        builder.setContentText(content);
        builder.setSmallIcon(icon);
        builder.setPriority(priority);
        builder.setOnlyAlertOnce(onlyAlertOnce);
        builder.setOngoing(ongoing);
        if (remoteViews != null) {
            builder.setContent(remoteViews);
        }
        if (intent != null) {
            builder.setContentIntent(intent);
        }
        if (ticker != null && ticker.length() > 0) {
            builder.setTicker(ticker);
        }
        if (when != 0) {
            builder.setWhen(when);
        }
        if (sound != null) {
            builder.setSound(sound);
        }
        if (defaults != 0) {
            builder.setDefaults(defaults);
        }
        //点击自动删除通知
        builder.setAutoCancel(true);
        return builder;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static Notification.Builder getChannelNotification(String title, String content, int icon) {
        Notification.Builder builder = new Notification.Builder(App.getApp(),
                Constants.Notification.CHANNEL_ID);
        Notification.Builder notificationBuilder = builder
                //设置标题
                .setContentTitle(title)
                //消息内容
                .setContentText(content)
                //设置通知的图标
                .setSmallIcon(icon)
                //让通知左右滑的时候是否可以取消通知
                .setOngoing(ongoing)
                //设置优先级
                .setPriority(priority)
                //是否提示一次.true - 如果Notification已经存在状态栏即使在调用notify函数也不会更新
                .setOnlyAlertOnce(onlyAlertOnce)
                .setAutoCancel(true);
        if (remoteViews != null) {
            //设置自定义view通知栏
            notificationBuilder.setContent(remoteViews);
        }
        if (intent != null) {
            notificationBuilder.setContentIntent(intent);
        }
        if (ticker != null && ticker.length() > 0) {
            //设置状态栏的标题
            notificationBuilder.setTicker(ticker);
        }
        if (when != 0) {
            //设置通知时间，默认为系统发出通知的时间，通常不用设置
            notificationBuilder.setWhen(when);
        }
        if (sound != null) {
            //设置sound
            notificationBuilder.setSound(sound);
        }
        if (defaults != 0) {
            //设置默认的提示音
            notificationBuilder.setDefaults(defaults);
        }
        if (pattern != null) {
            //自定义震动效果
            notificationBuilder.setVibrate(pattern);
        }
        return notificationBuilder;
    }


    private static boolean ongoing = false;
    private static RemoteViews remoteViews = null;
    private static PendingIntent intent = null;
    private static String ticker = "";
    private static int priority = Notification.PRIORITY_DEFAULT;
    private static boolean onlyAlertOnce = false;
    private static long when = 0;
    private static Uri sound = null;
    private static int defaults = 0;
    private static long[] pattern = null;

    /**
     * 让通知左右滑的时候是否可以取消通知
     *
     * @param ongoing 是否可以取消通知
     * @return
     */
    public NotificationUtils setOngoing(boolean ongoing) {
        this.ongoing = ongoing;
        return this;
    }

    /**
     * 设置自定义view通知栏布局
     *
     * @param remoteViews view
     * @return
     */
    public NotificationUtils setContent(RemoteViews remoteViews) {
        this.remoteViews = remoteViews;
        return this;
    }

    /**
     * 设置内容点击
     *
     * @param intent intent
     * @return
     */
    public NotificationUtils setContentIntent(PendingIntent intent) {
        this.intent = intent;
        return this;
    }

    /**
     * 设置状态栏的标题
     *
     * @param ticker 状态栏的标题
     * @return
     */
    public NotificationUtils setTicker(String ticker) {
        this.ticker = ticker;
        return this;
    }


    /**
     * 设置优先级
     * 注意：
     * Android 8.0以及上，在 NotificationChannel 的构造函数中指定，总共要五个级别；
     * Android 7.1（API 25）及以下的设备，还得调用NotificationCompat 的 setPriority方法来设置
     *
     * @param priority 优先级，默认是Notification.PRIORITY_DEFAULT
     * @return
     */
    public NotificationUtils setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    /**
     * 是否提示一次.true - 如果Notification已经存在状态栏即使在调用notify函数也不会更新
     *
     * @param onlyAlertOnce 是否只提示一次，默认是false
     * @return
     */
    public NotificationUtils setOnlyAlertOnce(boolean onlyAlertOnce) {
        this.onlyAlertOnce = onlyAlertOnce;
        return this;
    }

    /**
     * 设置通知时间，默认为系统发出通知的时间，通常不用设置
     *
     * @param when when
     * @return
     */
    public NotificationUtils setWhen(long when) {
        this.when = when;
        return this;
    }

    /**
     * 设置sound
     *
     * @param sound sound
     * @return
     */
    public NotificationUtils setSound(Uri sound) {
        this.sound = sound;
        return this;
    }


    /**
     * 设置默认的提示音
     *
     * @param defaults defaults
     * @return
     */
    public NotificationUtils setDefaults(int defaults) {
        this.defaults = defaults;
        return this;
    }

    /**
     * 自定义震动效果
     *
     * @param pattern pattern
     * @return
     */
    public NotificationUtils setVibrate(long[] pattern) {
        this.pattern = pattern;
        return this;
    }

    /**
     * 设置flag标签
     *
     * @param flags flags
     * @return
     */
    public NotificationUtils setFlags(int... flags) {
        this.flags = flags;
        return this;
    }


    /*****************************是否打开通知权限****************/
    public static void checkNotification(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            //判断是否需要开启通知栏功能
            openNotificationSetting(context, new OnNextListener() {
                @Override
                public void onNext() {
                    LogUtils.e("onNext: " + "已开启通知权限");
                }
            });
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static void openNotificationSetting(Context context, OnNextListener mOnNextLitener) {
        if (!isNotificationEnabled(context)) {
            showSettingDialog(context);
        } else {
            if (mOnNextLitener != null) {
                mOnNextLitener.onNext();
            }
        }
    }

    //判断该app是否打开了通知
    /**
     * 可以通过NotificationManagerCompat 中的 areNotificationsEnabled()来判断是否开启通知权限。NotificationManagerCompat 在 android.support.v4.app包中，是API 22.1.0 中加入的。而 areNotificationsEnabled()则是在 API 24.1.0之后加入的。
     * areNotificationsEnabled 只对 API 19 及以上版本有效，低于API 19 会一直返回true
     */
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static boolean isNotificationEnabled(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
            boolean areNotificationsEnabled = notificationManagerCompat.areNotificationsEnabled();
            return areNotificationsEnabled;
        }

        String CHECK_OP_NO_THROW = "checkOpNoThrow";
        String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);
        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;

        Class appOpsClass = null;
        /* Context.APP_OPS_MANAGER */
        try {
            appOpsClass = Class.forName(AppOpsManager.class.getName());
            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE,
                    String.class);
            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
            int value = (Integer) opPostNotificationValue.get(Integer.class);
            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;

    }


    public static void showSettingDialog(final Context context) {
        // 提示弹窗
//        showOriginalDialog();
       /* ViewGroup customView2 = (ViewGroup) View.inflate(App.getApp(), R.layout.layout_dialog, null);
        final Dialog dialog = StyledDialog.buildCustom(customView2, Gravity.CENTER).setForceWidthPercent(0.90f).setForceHeightPercent(0.5f).show();
        ShapeTextView tv_msg = customView2.findViewById(R.id.btn_cancel);
        ShapeTextView tv_ok = customView2.findViewById(R.id.btn_ok);
        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoSet(context);
            }
        });
        tv_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });*/
        // 显示弹框
        QuireDialog.getInstance(context)
                .setTitleText(context.getString(R.string.text_sys_tips))
                .setContentText(context.getString(R.string.text_tips_notice))
                .setSubmitTitleText(context.getString(R.string.text_start_open))
                .setCancelTitleText(context.getString(R.string.str_cancel))
//                    .setTopImageRes(R.drawable.ic_setting_tips4)
                .setOnQueraConsentListener(new QuireDialog.OnQueraConsentListener() {
                    @Override
                    public void onConsent(QuireDialog dialog) {
                        try {
                            gotoSet(context);
                        } catch (RuntimeException e) {
                            e.printStackTrace();
                        }
                    }
                }).show();

    }

    private static void showOriginalDialog() {
         /* final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("你还未开启系统通知，将影响消息的接收，要去开启吗？")
                .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                        gotoSet(context);
                    }
                }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();*/
    }

    /**
     * 打开手机设置页面
     * <p>
     * 假设没有开启通知权限，点击之后就需要跳转到 APP的通知设置界面，对应的Action是：Settings.ACTION_APP_NOTIFICATION_SETTINGS, 这个Action是 API 26 后增加的
     * 如果在部分手机中无法精确的跳转到 APP对应的通知设置界面，那么我们就考虑直接跳转到 APP信息界面，对应的Action是：Settings.ACTION_APPLICATION_DETAILS_SETTINGS
     */
    public static void gotoSet(Context context) {
        Intent intent = new Intent();
        if (Build.VERSION.SDK_INT >= 26) {
            // android 8.0引导
            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            Uri uri = Uri.fromParts("package", context.getPackageName(), null);
            intent.setData(uri);
//            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
//            intent.putExtra("android.provider.extra.APP_PACKAGE", context.getPackageName());
        } else if (Build.VERSION.SDK_INT >= 21) {
            // android 5.0-7.0
            intent.setAction(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
            intent.putExtra("app_package", context.getPackageName());
            intent.putExtra("app_uid", context.getApplicationInfo().uid);
        } else {
            // 其他
            intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            intent.setData(Uri.fromParts("package", context.getPackageName(), null));
        }
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    /*=====================添加Listener回调================================*/
    public interface OnNextListener {
        /**
         * 不需要设置通知的下一步
         */
        void onNext();
    }

    private OnNextListener mOnNextListener;

    public void setmOnNextLitener(OnNextListener mOnNextListener) {
        this.mOnNextListener = mOnNextListener;
    }


}
