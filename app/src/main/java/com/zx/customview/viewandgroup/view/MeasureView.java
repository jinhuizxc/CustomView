package com.zx.customview.viewandgroup.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.Nullable;

import com.orhanobut.logger.Logger;

/**
 * 绘制过程： onMeasure->onLayout->onDraw
 * 6 1 2
 */
public class MeasureView extends View {

    public MeasureView(Context context) {
        super(context);
    }

    public MeasureView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MeasureView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 重写onMeasure()方法，实现测量
     * <p>
     * 注： 不能去掉super.onMeasure(widthMeasureSpec, heightMeasureSpec);：
     * 否则会报异常
     * java.lang.IllegalStateException: View with id -1: com.zx.customview.viewandgroup.view.MeasureView#onMeasure()
     * did not set the measured dimension by calling setMeasuredDimension()
     *
     * 设置的大小与xml里面设置的值会有一个比对，然后最终取需要的数值;
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 这里注释掉，替换
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);

        int width_mode = MeasureSpec.getMode(widthMeasureSpec); //获取测量模式
        int width_size = MeasureSpec.getSize(widthMeasureSpec); //获取测量值
        int height_mode = MeasureSpec.getMode(heightMeasureSpec); //获取测量模式
        int height_size = MeasureSpec.getSize(heightMeasureSpec); //获取测量值
        Logger.d("onMeasure: "
                + " width_mode: " + width_mode
                + " width_size: " + width_size
                + " , height_mode: " + height_mode
                + " , height_size: " + height_size);
        // onMeasure: width_mode: -2147483648 width_size: 720 height_mode: -2147483648 height_size: 1267
        // 测试：
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
        Logger.d("测量后的宽度、高度值：" + measureWidth(widthMeasureSpec)
                + " , " + measureHeight(heightMeasureSpec));
        Logger.d("MeasureView onMeasure");
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Logger.d("MeasureView onLayout");
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        Logger.d("MeasureView onDraw");
    }

    /**
     * 系统提供的测量方法
     *
     * @param size
     * @param measureSpec
     * @return
     */
    public static int getDefaultSize(int size, int measureSpec) {
        int result = size;
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);

        switch (specMode) {
            case MeasureSpec.UNSPECIFIED:
                result = size;
                break;
            case MeasureSpec.AT_MOST:
            case MeasureSpec.EXACTLY:
                result = specSize;
                break;
        }
        return result;
    }

    /**
     * 获取测量的宽度
     *
     * @param widthMeasureSpec
     */
    private int measureWidth(int widthMeasureSpec) {
        int width = 0;
        int mode = MeasureSpec.getMode(widthMeasureSpec); //获取测量模式
        int size = MeasureSpec.getSize(widthMeasureSpec); //获取测量值
        switch (mode) {
            case MeasureSpec.UNSPECIFIED:
                Logger.d("measureWidth 1");
                width = 200;  // 不确定的值，想多大就多大，用于scrollview、listview等，这里设置为200
                break;
            case MeasureSpec.AT_MOST:   //设置为wrap_content
                Logger.d("measureWidth 2");
                width = Math.max(size, width);  // 至多，所以取最大值
                break;
            case MeasureSpec.EXACTLY:  //精准测量模式,设置为具体数值或者match_parent时
                Logger.d("measureWidth 3");
                width = size;
                break;
            default:
                break;
        }

        return width;
    }

    /**
     * 获得测量的高度
     *
     * @param heightMeasureSpec
     * @return
     */
    private int measureHeight(int heightMeasureSpec) {
        int height = 0;
        int mode = MeasureSpec.getMode(heightMeasureSpec); //测量模式
        int size = MeasureSpec.getSize(heightMeasureSpec); //测量的尺寸
        if (mode == MeasureSpec.EXACTLY) {
            Logger.d("measureHeight 1");
            height = size;
        } else {
            Logger.d("measureHeight 2");
            height = 200;
            if (mode == MeasureSpec.AT_MOST) {
                Logger.d("measureHeight 3");
                height = Math.min(height, size);
            }
        }
        Log.d("hbj", "height=" + height);
        return height;

    }


}
