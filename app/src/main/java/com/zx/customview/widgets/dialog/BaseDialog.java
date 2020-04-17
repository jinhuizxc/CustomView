package com.zx.customview.widgets.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDialog;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

import com.zx.customview.R;

/**
 * TinyHung@Outlook.com
 * 2017/3/24 9:12
 * 弹窗的统一父类
 */

public abstract class BaseDialog extends AppCompatDialog {

    protected Context mContext;

    public BaseDialog(@NonNull Context context) {
        super(context, R.style.CenterDialogAnimationStyle);
        mContext = context;
    }

    public BaseDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext = context;
    }

    @Override
    public void setContentView(int layoutResId) {
        super.setContentView(layoutResId);
        initViews();
    }

    public abstract void initViews();


    public int setDialogWidth(Dialog dialog) {
        Window window = dialog.getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes(); //得到布局管理者
        WindowManager systemService = (WindowManager) dialog.getContext().getSystemService(Context.WINDOW_SERVICE); // 获取dialog对应的window_service
        DisplayMetrics displayMetrics = new DisplayMetrics();
        systemService.getDefaultDisplay().getMetrics(displayMetrics);
        int hight = LinearLayout.LayoutParams.WRAP_CONTENT;
        attributes.height = hight;
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int screenWidth = systemService.getDefaultDisplay().getWidth();
        if (screenWidth <= 720) {
            attributes.width = screenWidth - 100;
        } else if (screenWidth > 720 && screenWidth < 1100) {
            attributes.width = screenWidth - 200;
        } else if (screenWidth > 1100 && screenWidth < 1500) {
            attributes.width = screenWidth - 280;
        } else {
            attributes.width = screenWidth - 200;
        }
        attributes.gravity = Gravity.CENTER;
        return attributes.width;

    }

    /**
     * 设置Dialog依附在屏幕中的位置
     */
    protected void initLayoutParams(int gravity) {
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();//得到布局管理者
        WindowManager systemService = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);//得到窗口管理者
        DisplayMetrics displayMetrics = new DisplayMetrics();//创建设备屏幕的管理者
        systemService.getDefaultDisplay().getMetrics(displayMetrics);//得到屏幕的宽高
        int hight = LinearLayout.LayoutParams.WRAP_CONTENT;//取出布局的高度
        attributes.height = hight;
        attributes.width = systemService.getDefaultDisplay().getWidth();
        attributes.gravity = gravity;
    }


    /**
     * 设置Dialog依附在屏幕中的位置
     */
    protected void initLayoutMarginParams(int gravity) {
        Window window = getWindow();
        WindowManager.LayoutParams attributes = window.getAttributes();//得到布局管理者
        WindowManager systemService = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);//得到窗口管理者
        DisplayMetrics displayMetrics = new DisplayMetrics();//创建设备屏幕的管理者
        systemService.getDefaultDisplay().getMetrics(displayMetrics);//得到屏幕的宽高
        int hight = LinearLayout.LayoutParams.WRAP_CONTENT;//取出布局的高度
        attributes.height = hight;
        attributes.width = systemService.getDefaultDisplay().getWidth() - 120;
        attributes.gravity = gravity;
    }


    @Override
    public void dismiss() {
        super.dismiss();
        mContext = null;
    }
}
