package com.example.test;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

/**
 * 自定义View-onLayout篇
 * https://www.jianshu.com/p/a5b1e778744f
 */
public class MyViewGroup extends ViewGroup {

    private int horizontalSpace = 10;
    private int verticalSpace = 10;

    private static final String TAG = "MyViewGroup";
    

    public MyViewGroup(Context context) {
        this(context, null);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure: ");
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            measureChild(view, widthMeasureSpec, heightMeasureSpec);
        }
    }

    /**
     * @param child                   子View
     * @param parentWidthMeasureSpec  宽度测量规格
     * @param widthUsed               父view在宽度上已经使用的距离
     * @param parentHeightMeasureSpec 高度测量规格
     * @param heightUsed              父view在高度上已经使用的距离
     */
    @Override
    protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        super.measureChildWithMargins(child, parentWidthMeasureSpec, widthUsed, parentHeightMeasureSpec, heightUsed);
    }

    /**
     * 已经测量出子View的具体大小了，那么下面，我们就来安排他们的位置。
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        Log.d(TAG, "onLayout: ");
        int hadUsedHorizontal = 0;//水平已经使用的距离
        int hadUsedVertical = 0;//垂直已经使用的距离
        int width = getMeasuredWidth();
//        int height = getMeasuredHeight();
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            //判断是否已经超出宽度
            if (view.getMeasuredWidth() + hadUsedHorizontal > width) {
                // 已经超出了宽度
                hadUsedVertical = hadUsedVertical + view.getMeasuredHeight() + verticalSpace;
                hadUsedHorizontal = 0;
            }
            view.layout(hadUsedHorizontal, hadUsedVertical,
                    hadUsedHorizontal + view.getMeasuredWidth(),
                    hadUsedVertical + view.getMeasuredHeight());
            hadUsedHorizontal = hadUsedHorizontal + horizontalSpace + view.getMeasuredWidth();
        }
    }
}
