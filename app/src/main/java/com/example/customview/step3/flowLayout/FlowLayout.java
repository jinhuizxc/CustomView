package com.example.customview.step3.flowLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Email: 1004260403@qq.com
 * Created by jinhui on 2019/1/10.
 */
public class FlowLayout extends ViewGroup {

    public FlowLayout(Context context) {
        super(context);
    }

    public FlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    // 需要重写方法才可以
    // java.lang.ClassCastException: android.view.ViewGroup$LayoutParams cannot be cast to android.view.ViewGroup$MarginLayoutParams

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
//        return super.generateLayoutParams(p);
        return new MarginLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

//        onMeasure1(widthMeasureSpec, heightMeasureSpec);
        onMeasure2(widthMeasureSpec, heightMeasureSpec);

    }

    private void onMeasure2(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        int height = 0;
        int width = 0;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {

            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
//            int childHeight = child.getMeasuredHeight();
//            int childWidth = child.getMeasuredWidth();

            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;

            height += childHeight;
            width = Math.max(childWidth, width);
        }

        setMeasuredDimension((measureWidthMode == MeasureSpec.EXACTLY) ? measureWidth
                : width, (measureHeightMode == MeasureSpec.EXACTLY) ? measureHeight
                : height);
    }

    private void onMeasure1(int widthMeasureSpec, int heightMeasureSpec) {
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
        int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
        int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);

        int height = 0;
        int width = 0;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {

            View child = getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
            int childHeight = child.getMeasuredHeight();
            int childWidth = child.getMeasuredWidth();

            height += childHeight;
            width = Math.max(childWidth, width);
        }

        setMeasuredDimension((measureWidthMode == MeasureSpec.EXACTLY) ? measureWidth
                : width, (measureHeightMode == MeasureSpec.EXACTLY) ? measureHeight
                : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

//        onLayout1();
        onLayout2();

    }

    private void onLayout2() {
        int top = 0;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
//            int childHeight = child.getMeasuredHeight();
//            int childWidth = child.getMeasuredWidth();
            MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            int childHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
            int childWidth = child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;

            child.layout(0, top, childWidth, top + childHeight);
            top += childHeight;
        }
    }

    private void onLayout1() {
        int top = 0;
        int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            int childHeight = child.getMeasuredHeight();
            int childWidth = child.getMeasuredWidth();

            child.layout(0, top, childWidth, top + childHeight);
            top += childHeight;
        }
    }
}
