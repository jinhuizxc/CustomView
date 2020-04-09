package com.zx.customview.keyboard.email;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.zx.customview.R;
import com.zx.customview.keyboard.email.view.SPUtils;


public class EmailConflictView extends LinearLayout {

    private static final String COLUMN_NAME = "column_name";

    // 用于控制显示或隐藏输入法面板的类
    private InputMethodManager imm;
    private Context mContext;
    private View mPanelView;  // 跟输入法切换的面板
    private ImageButton imgSelectButton;  // 切换面板与键盘的按钮
    private int mPanelViewId;
    private int mSelectId;
    private boolean mIsKeyboardActive = false;  // 输入法是否激活

    private KeyboardOnGlobalChangeListener mKeyboardOnGlobalChangeListener;

    private static final String TAG = "EmailConflictView";


    public EmailConflictView(Context context) {
        this(context, null);
    }

    public EmailConflictView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmailConflictView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.mContext = context;
        init(context, attrs);


    }

    private void init(Context context, AttributeSet attrs) {
        //监听布局变化
        getViewTreeObserver().addOnGlobalLayoutListener(new KeyboardOnGlobalChangeListener());

        imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.EmailConflictView);
        mPanelViewId = typedArray.getResourceId(R.styleable.EmailConflictView_panelView1, NO_ID);
        mSelectId = typedArray.getResourceId(R.styleable.EmailConflictView_link, NO_ID);

        typedArray.recycle();
    }


    /**
     * onFinishInflate何时被调用
     * 自定义view中onFinishInfalte会在什么时候被调用呢。
     * 当我们的XML布局被加载完后，就会回调onFinshInfalte这个方法，
     * 在这个方法中我们可以初始化控件和数据。
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initView();
        initListener();
    }

    private void initView() {
        if (mPanelViewId != NO_ID) {
            mPanelView = this.findViewById(mPanelViewId);
            int keyboardHeight = SPUtils.getInt(mContext, COLUMN_NAME);
            if (keyboardHeight > 0) {
                // 设置键盘高度
                ViewGroup.LayoutParams lp = mPanelView.getLayoutParams();
                lp.height = keyboardHeight;
                mPanelView.setLayoutParams(lp);
            }
        }
        if (mSelectId != NO_ID) {
            imgSelectButton = this.findViewById(mSelectId);
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initListener() {

        imgSelectButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                // 切换按钮的点击处理
                imgSelectButton.setSelected(!imgSelectButton.isSelected());
                if (mIsKeyboardActive) {
                    //  输入法打开状态下
                    if (imgSelectButton.isSelected()){  // 打开表情
                        imgSelectButton.setImageResource(R.drawable.icon_link_yes);
                        // 显示键盘
                        // 设置为不会调整大小，以便输入弹起时布局不会改变。若不设置此属性，输入法弹起时布局会闪一下
                        ((Activity)mContext).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
                        //隐藏键盘
                        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(imgSelectButton.getWindowToken(), 0);
                        mPanelView.setVisibility(View.VISIBLE);
                    }else {
                        imgSelectButton.setImageResource(R.drawable.icon_link_no);
                        mPanelView.setVisibility(View.GONE);
                        InputMethodManager imm = (InputMethodManager) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                        imm.hideSoftInputFromWindow(imgSelectButton.getApplicationWindowToken(), 0);
                        ((Activity) mContext).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                                | WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                    }
                } else {
                    //  输入法关闭状态下
                    if (imgSelectButton.isSelected()){
                        imgSelectButton.setImageResource(R.drawable.icon_link_yes);
                        // 设置为不会调整大小，以便输入弹起时布局不会改变。若不设置此属性，输入法弹起时布局会闪一下
                        ((Activity) mContext).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_NOTHING);
                        mPanelView.setVisibility(View.VISIBLE);
                    }else {
                        imgSelectButton.setImageResource(R.drawable.icon_link_no);
                        imm.toggleSoftInput(InputMethodManager.SHOW_IMPLICIT, InputMethodManager.HIDE_IMPLICIT_ONLY);
                        imgSelectButton.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                mPanelView.setVisibility(View.GONE);
                                ((Activity) mContext).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE
                                        | WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
                            }
                        }, 250);    // 延迟一段时间，等待输入法完全弹出*/
                    }

                }
            }
        });


    }

    /**
     * 关闭键盘
     */
    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(imgSelectButton.getWindowToken(), 0);
    }

    /**
     * 显示键盘
     */
    private void showKeyboard() {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(imgSelectButton, 0);
    }



    private class KeyboardOnGlobalChangeListener implements ViewTreeObserver.OnGlobalLayoutListener {

        int mScreenHeight = 0;
        Rect mRect = new Rect();

        public int getScreenHeight() {
            if (mScreenHeight > 0) {
                return mScreenHeight;
            }
            mScreenHeight = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE))
                    .getDefaultDisplay().getHeight();
            return mScreenHeight;
        }

        @Override
        public void onGlobalLayout() {
            // 获取当前页面窗口的显示范围
            getWindowVisibleDisplayFrame(mRect);
            // 获取屏幕高度
            int screenHeight = getScreenHeight();
            // 获取键盘高度
            int keyboardHeight = screenHeight - mRect.bottom;  // 输入法的高度
            boolean isActive = false;
            if (Math.abs(keyboardHeight) > screenHeight / 5){
                isActive = true;  // 超过屏幕五分之一则表示弹出了输入法
            }

            mIsKeyboardActive = isActive;
            onKeyboardStateChanged(mIsKeyboardActive, keyboardHeight);

        }
    }

    private void onKeyboardStateChanged(boolean isActive, int keyboardHeight) {
        if (isActive){
            Log.d(TAG, "onKeyboardStateChanged ---->1 :" + isActive + " , " + keyboardHeight);
            imgSelectButton.setFocusable(false);
            imgSelectButton.setFocusableInTouchMode(false);
            if (imgSelectButton.isSelected()){
                mPanelView.setVisibility(View.GONE);
                imgSelectButton.setSelected(false);
            }
            SPUtils.putInt(mContext, COLUMN_NAME, keyboardHeight);
            ViewGroup.LayoutParams layoutParams = mPanelView.getLayoutParams();
            if (!(layoutParams.height == keyboardHeight)){
                layoutParams.height = keyboardHeight;
                mPanelView.setLayoutParams(layoutParams);
            }
        }else {
            Log.d(TAG, "onKeyboardStateChanged ---> 2 : " + isActive + " , " + keyboardHeight);
            if (imgSelectButton.isSelected()){
                return;
            }
        }
    }
}
