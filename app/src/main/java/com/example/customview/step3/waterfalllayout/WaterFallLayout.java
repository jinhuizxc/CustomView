package com.example.customview.step3.waterfalllayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Email: 1004260403@qq.com
 * Created by jinhui on 2019/1/10.
 */
public class WaterFallLayout extends ViewGroup {

    private int columns = 3;
    private int hSpace = 20;
    private int vSpace = 20;
    private int childWidth = 0;
    private int top[];


    public WaterFallLayout(Context context) {
//        super(context);
        this(context, null);
    }

    public WaterFallLayout(Context context, AttributeSet attrs) {
//        super(context, attrs);
        this(context, attrs, 0);
    }

    public WaterFallLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        top = new int[columns];
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
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
            top[minColum] += vSpace + childHeight;
        }

        int wrapHeight;
        wrapHeight = getMaxHeight();
        setMeasuredDimension(widthMode == MeasureSpec.AT_MOST ? wrapWidth : sizeWidth, wrapHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int childCount = getChildCount();
        clearTop();
        for (int i = 0; i < childCount; i++) {
            View child = this.getChildAt(i);
            int childHeight = child.getMeasuredHeight() * childWidth / child.getMeasuredWidth();
            int minColum = getMinHeightColum();
            int tleft = minColum * (childWidth + hSpace);
            int ttop = top[minColum];
            int tright = tleft + childWidth;
            int tbottom = ttop + childHeight;
            top[minColum] += vSpace + childHeight;
            child.layout(tleft, ttop, tright, tbottom);
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
