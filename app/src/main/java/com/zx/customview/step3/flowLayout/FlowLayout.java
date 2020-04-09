package com.zx.customview.step3.flowLayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Email: 1004260403@qq.com
 * Created by jinhui on 2019/1/10.
 *
 * 一、ViewGroup绘制流程
 *
 * 注意，View及ViewGroup基本相同，只是在ViewGroup中不仅要绘制自己还是绘制其中的子控件，而View则只需要绘制自己就可以了，所以我们这里就以ViewGroup为例来讲述整个绘制流程。
 * 绘制流程分为三步：测量、布局、绘制
 * 分别对应：onMeasure()、onLayout()、onDraw()
 * 其中，他们三个的作用分别如下：
 * onMeasure()：测量自己的大小，为正式布局提供建议。（注意，只是建议，至于用不用，要看onLayout）;
 * onLayout():使用layout()函数对所有子控件布局；
 * onDraw():根据布局的位置绘图；
 *
 * 二、onMeasure与MeasureSpec
 * 布局绘画涉及两个过程：测量过程和布局过程。测量过程通过measure方法实现，
 * 是View树自顶向下的遍历，每个View在循环过程中将尺寸细节往下传递，
 * 当测量过程完成之后，所有的View都存储了自己的尺寸。第二个过程则是通过方法layout来实现的，也是自顶向下的。
 * 在这个过程中，每个父View负责通过计算好的尺寸放置它的子View。
 * 前面讲过，onMeasure()是用来测量当前控件大小的，给onLayout（）提供数值参考，
 * 需要特别注意的是：测量完成以后通过setMeasuredDimension(int,int)设置给系统。
 *
 *
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

    /**
     * 意义与组成
     * 我们先说他们的意义：
     * 他们是父类传递过来给当前view的一个建议值,即想把当前view的尺寸设置为宽widthMeasureSpec,高heightMeasureSpec
     * 有关他们的组成，我们就直接转到MeasureSpec部分。
     *
     * MeasureSpec
     * 表面上看起来他们是int类型的数字，其实他们是由mode+size两部分组成的
     * widthMeasureSpec和heightMeasureSpec转化成二进制数字表示，他们都是32位的。
     * 前两位代表mode(测量模式)，后面30位才是他们的实际数值（size）。
     *
     * （1）模式分类
     * 它有三种模式：
     * ①、UNSPECIFIED(未指定)，父元素不对子元素施加任何束缚，子元素可以得到任意想要的大小；
     * ②、EXACTLY(完全)，父元素决定子元素的确切大小，子元素将被限定在给定的边界里而忽略它本身大小；
     * ③、AT_MOST(至多)，子元素至多达到指定大小的值。
     * 他们对应的二进制值分别是：
     * UNSPECIFIED=00000000000000000000000000000000
     * EXACTLY =01000000000000000000000000000000
     * AT_MOST =10000000000000000000000000000000
     * 由于最前面两位代表模式，所以他们分别对应十进制的0，1，2；
     *
     * （2）模式提取
     * //对应11000000000000000000000000000000;总共32位，前两位是1
     * int MODE_MASK  = 0xc0000000;
     *
     * //提取模式
     * public static int getMode(int measureSpec) {
     *     return (measureSpec & MODE_MASK);
     * }
     * //提取数值
     * public static int getSize(int measureSpec) {
     *     return (measureSpec & ~MODE_MASK);
     * }
     * 主要用到了MASK的与、非运算，难度不大，如果有问题，自行谷歌一下与、非运算方法吧。
     * （3）、MeasureSpec
     * Android 提供提取模式和数值的类MeasureSpec
     * MeasureSpec.getMode(int spec) //获取MODE
     * MeasureSpec.getSize(int spec) //获取数值
     *
     *       int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
     *         int measureHeight = MeasureSpec.getSize(heightMeasureSpec);
     *         int measureWidthMode = MeasureSpec.getMode(widthMeasureSpec);
     *         int measureHeightMode = MeasureSpec.getMode(heightMeasureSpec);
     * （4）、模式有什么用呢
     *  模式的由来分别来自于XML定义：
     *  简单来说，XML布局和模式有如下对应关系：
     *     wrap_content-> MeasureSpec.AT_MOST
     *     match_parent -> MeasureSpec.EXACTLY
     *     具体值 -> MeasureSpec.EXACTLY
     *
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // Android 提供提取模式和数值的类MeasureSpec
//        MeasureSpec.getMode(widthMeasureSpec);
//        MeasureSpec.getSize(widthMeasureSpec);
        // MODE的取值为 MeasureSpec.AT_MOST; MeasureSpec.EXACTLY; MeasureSpec.UNSPECIFIED;
        int measureWidth = MeasureSpec.getSize(widthMeasureSpec);
        int measureHeight = MeasureSpec.getSize(heightMeasureSpec);


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
