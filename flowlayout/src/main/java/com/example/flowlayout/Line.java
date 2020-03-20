package com.example.flowlayout;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 内部类，行管理器，管理每一行的孩子
 */
public class Line {

    // 定义一个行的集合来存放子View
    private List<View> views = new ArrayList<>();
    // 行的最大宽度
    private int maxWidth;
    // 行中已经使用的宽度
    private int usedWidth;
    // 行的高度
    private int height;
    // 孩子之间的距离(行之间的间距)
    private float space;
    // 是否是最后一个view
    private boolean isLast;

    // 通过构造初始化最大宽度和边距
    public Line(int maxWidth, float space) {
        this.maxWidth = maxWidth;
        this.space = space;
    }

    public boolean isLast() {
        return isLast;
    }

    public void setLast(boolean last) {
        isLast = last;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * 往集合里添加孩子
     *
     * @param child
     */
    public void addView(View child) {
        int childWidth = child.getMeasuredWidth();
        int childHeight = child.getMeasuredHeight();

        // 更新行的使用宽度和高度, 集合里没有孩子的时候
        if (views.size() == 0) {
            // 当前没有元素, 只能是添加一行
            // 有2种情况:
            // childWidth > maxWidth; childWidth < maxWidth
            if (childWidth > maxWidth) {
                // 如果孩子宽度大于最大宽度
                usedWidth = maxWidth;
                height = childHeight;
            } else {
                usedWidth = childWidth;
                height = childHeight;
            }
        } else {
            // 如果当前已经有孩子了
            // 按说这里也要判断宽度是否超过位置的啊
            usedWidth += space + childWidth;
            height = childHeight > height ? childHeight : height;
        }

        // 添加孩子到集合中
        views.add(child);
    }

    /**
     * 指定孩子显示的位置
     * @param l
     * @param t
     */
    public void layout(int l, int t) {
        // 平分剩下的空间
        int avg = (maxWidth - usedWidth) / views.size();
//        Log.d("layout", "平分剩下的空间 --> avg: " + avg);
        for (View view : views) {
            // 获取宽高
            int measuredWidth = view.getMeasuredWidth();
            int measuredHeight = view.getMeasuredHeight();
            if (isLast){
                // 重新测量
                view.measure(View.MeasureSpec.makeMeasureSpec(measuredWidth, View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(measuredHeight, View.MeasureSpec.EXACTLY));
            }else {
                // 重新测量
                view.measure(View.MeasureSpec.makeMeasureSpec(measuredWidth + avg, View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(measuredHeight, View.MeasureSpec.EXACTLY));
            }

            // 重新获取宽度值
            measuredWidth = view.getMeasuredWidth();

            int top = t;
            int left = l;
            int right = measuredWidth + left;
            int bottom = measuredHeight + top;
            // 指定位置
            view.layout(left, top, right, bottom);

            // 更新数据
            l += measuredWidth + space;


        }
    }

    /**
     * 判断当前的行是否能添加孩子
     *
     * @param child
     * @return
     */
    public boolean canAddView(View child) {
        // 集合里没有数据可以添加
        if (views.size() == 0) {
            return true;
        }
        // view size 不为0
        // 最后一个孩子的宽度大于剩余宽度就不添加
        if (child.getMeasuredWidth() > (maxWidth - usedWidth - space)) {
            return false;
        }
        // 默认可以添加
        return true;
    }
}
