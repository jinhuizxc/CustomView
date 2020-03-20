package com.example.viewgroup.study1;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Android 手把手教您自定义ViewGroup（一）
 * https://blog.csdn.net/lmj623565791/article/details/38339817
 */
public class MyViewGroup extends ViewGroup {

    public MyViewGroup(Context context) {
        this(context, null);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 决定该ViewGroup的LayoutParams
     *
     * 对于我们这个例子，我们只需要ViewGroup能够支持margin即可，
     * 那么我们直接使用系统的MarginLayoutParams
     *
     * 重写父类的该方法，返回MarginLayoutParams的实例，
     * 这样就为我们的ViewGroup指定了其LayoutParams为MarginLayoutParams。
     *
     *
     * @param attrs
     * @return
     */
    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
//        return super.generateLayoutParams(attrs);
        return new MarginLayoutParams(getContext(), attrs);
    }

    /**
     * 计算所有ChildView的宽度和高度 然后根据ChildView的计算结果，设置自己的宽和高
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        /**
         * 获得此ViewGroup上级容器为其推荐的宽和高，以及计算模式
         */
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        // 计算出所有的childView的宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        /**
         * 记录如果是wrap_content是设置的宽和高
         */
        int width = 0;
        int height = 0;

        int cCount = getChildCount();

        // child测量的宽高
        int cWidth = 0;
        int cHeight = 0;
        // 边距布局参数
        MarginLayoutParams cParams = null;
        // 用于计算左边两个childView的高度
        int lHeight = 0;
        // 用于计算右边两个childView的高度，最终高度取二者之间大值
        int rHeight = 0;
        // 用于计算上边两个childView的宽度
        int tWidth = 0;
        // 用于计算下面两个childiew的宽度，最终宽度取二者之间大值
        int bWidth = 0;

        /**
         * 根据childView计算的出的宽和高，以及设置的margin计算容器的宽和高，主要用于容器是warp_content时
         *
         *
         */
        for (int i = 0; i < cCount; i++) {
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();

            cParams = (MarginLayoutParams) childView.getLayoutParams();
            // 上面两个childView
            if (i == 0 || i == 1) {
                tWidth += cWidth + cParams.leftMargin + cParams.rightMargin;
            }
            if (i == 2 || i == 3) {
                bWidth += cWidth + cParams.leftMargin + cParams.rightMargin;
            }

            if (i == 0 || i == 2) {
                lHeight += cHeight + cParams.topMargin + cParams.bottomMargin;
            }

            if (i == 1 || i == 3) {
                rHeight += cHeight + cParams.topMargin + cParams.bottomMargin;
            }


            width = Math.max(tWidth, bWidth);
            height = Math.max(lHeight, rHeight);

            /**
             * 如果是wrap_content设置为我们计算的值
             * 否则：直接设置为父容器计算的值
             */
            setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? widthSize
                    : width, (heightMode == MeasureSpec.EXACTLY) ? heightSize
                    : height);

        }


    }

    /**
     * onLayout对其所有childView进行定位（设置childView的绘制区域）
     *
     * @param changed
     * @param l
     * @param t
     * @param r
     * @param b
     */
    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int cCount = getChildCount();
        int cWidth = 0;
        int cHeight = 0;
        MarginLayoutParams cParams = null;
        /**
         * 遍历所有childView根据其宽和高，以及margin进行布局
         */
        for (int i = 0; i < cCount; i++) {
            View childView = getChildAt(i);
            cWidth = childView.getMeasuredWidth();
            cHeight = childView.getMeasuredHeight();
            cParams = (MarginLayoutParams) childView.getLayoutParams();

            /**
             * 如果是第一个View(index=0) ：则childView.layout(cl, ct, cr, cb);
             * cl为childView的leftMargin , ct 为topMargin , cr 为cl+ cWidth , cb为 ct + cHeight
             *
             * 如果是第二个View(index=1) ：则childView.layout(cl, ct, cr, cb);
             * cl为getWidth() - cWidth - cParams.leftMargin- cParams.rightMargin;
             * ct 为topMargin , cr 为cl+ cWidth , cb为 ct + cHeight
             *
             * 剩下两个类似~
             * 这样就完成了，我们的ViewGroup代码的编写，下面我们进行测试，分别设置宽高为固定值，wrap_content，match_parent
             *
             */

            // child对应的左上右下的宽高，与坐标无关
            int cl = 0, ct = 0, cr = 0, cb = 0;

            switch (i) {
                case 0:
                    cl = cParams.leftMargin;
                    ct = cParams.topMargin;
                    break;
                case 1:
                    cl = getWidth() - cWidth - cParams.leftMargin
                            - cParams.rightMargin;
                    ct = cParams.topMargin;

                    break;
                case 2:
                    cl = cParams.leftMargin;
                    ct = getHeight() - cHeight - cParams.bottomMargin;
                    break;
                case 3:
                    cl = getWidth() - cWidth - cParams.leftMargin
                            - cParams.rightMargin;
                    ct = getHeight() - cHeight - cParams.bottomMargin;
                    break;

            }

            cr = cl + cWidth;
            cb = cHeight + ct;

            childView.layout(cl, ct, cr, cb);
        }

    }
}
