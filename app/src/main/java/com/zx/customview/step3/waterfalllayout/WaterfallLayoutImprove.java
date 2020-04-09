package com.zx.customview.step3.waterfalllayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Email: 1004260403@qq.com
 * Created by jinhui on 2019/1/10.
 */
public class WaterfallLayoutImprove extends ViewGroup {
    private int columns = 3;
    private int hSpace = 20;
    private int vSpace = 20;
    private int childWidth = 0;
    private int top[];

    public WaterfallLayoutImprove(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        top = new int[columns];
    }

    public WaterfallLayoutImprove(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WaterfallLayoutImprove(Context context) {
        this(context, null);
    }

    public static class WaterfallLayoutParams extends ViewGroup.LayoutParams {
        public int left = 0;
        public int top = 0;
        public int right = 0;
        public int bottom = 0;

        public WaterfallLayoutParams(Context arg0, AttributeSet arg1) {
            super(arg0, arg1);
        }

        public WaterfallLayoutParams(int arg0, int arg1) {
            super(arg0, arg1);
        }

        public WaterfallLayoutParams(android.view.ViewGroup.LayoutParams arg0) {
            super(arg0);
        }

    }

    @Override
    public ViewGroup.LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new WaterfallLayoutParams(getContext(), attrs);
    }

    @Override
    protected ViewGroup.LayoutParams generateDefaultLayoutParams() {
        return new WaterfallLayoutParams(WaterfallLayoutParams.WRAP_CONTENT, WaterfallLayoutParams.WRAP_CONTENT);
    }

    @Override
    protected ViewGroup.LayoutParams generateLayoutParams(ViewGroup.LayoutParams p) {
        return new WaterfallLayoutParams(p);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p) {
        return p instanceof WaterfallLayoutParams;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        measureChildren(widthMeasureSpec, heightMeasureSpec);

        //得到单个Item的宽度
        childWidth = (sizeWidth - (columns - 1) * hSpace) / columns;

        //得到总宽度
        int wrapWidth;
        int childCount = getChildCount();
        if (childCount < columns) {
            wrapWidth = childCount * childWidth + (childCount - 1) * hSpace;
        } else {
            wrapWidth = sizeWidth;
        }

        clearTop();
        for (int i = 0; i < childCount; i++) {
            View child = this.getChildAt(i);
            int childHeight = child.getMeasuredHeight() * childWidth / child.getMeasuredWidth();
            int minColum = getMinHeightColum();

            WaterfallLayoutParams lParams = (WaterfallLayoutParams)child.getLayoutParams();
            lParams.left = minColum * (childWidth + hSpace);
            lParams.top = top[minColum];
            lParams.right = lParams.left + childWidth;
            lParams.bottom = lParams.top + childHeight;

            top[minColum] += vSpace + childHeight;
        }

        int wrapHeight;
        wrapHeight = getMaxHeight();
        setMeasuredDimension(widthMode == MeasureSpec.AT_MOST ? wrapWidth : sizeWidth, wrapHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            WaterfallLayoutParams lParams = (WaterfallLayoutParams)child.getLayoutParams();
            child.layout(lParams.left, lParams.top, lParams.right, lParams.bottom);
        }
    }

    private int getMinHeightColum() {
        int minColum = 0;
        for (int i = 0; i < columns; i++) {
            if (top[i] < top[minColum]) {
                minColum = i;
            }
        }
        return minColum;
    }

    private int getMaxHeight() {
        int maxHeight = 0;
        for (int i = 0; i < columns; i++) {
            if (top[i] > maxHeight) {
                maxHeight = top[i];
            }
        }
        return maxHeight;
    }

    private void clearTop() {
        for (int i = 0; i < columns; i++) {
            top[i] = 0;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int index);
    }

    public void setOnItemClickListener(final OnItemClickListener listener) {
        for (int i = 0; i < getChildCount(); i++) {
            final int index = i;
            View view = getChildAt(i);
            view.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onItemClick(v, index);
                }
            });
        }
    }

}

