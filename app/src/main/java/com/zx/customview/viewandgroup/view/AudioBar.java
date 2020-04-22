package com.zx.customview.viewandgroup.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.orhanobut.logger.Logger;

public class AudioBar extends View {

    private Paint mPaint;  //定义画笔
    private int mWidth;   // view的宽度
    private int mRectWidth;  //音频条的宽度
    private int mRectHeight;  //音频条的高度
    private int mRectCount; //音频条的个数
    private int offset = 5; //音频条的偏移量
    private double mRandom;  //随机数

    // 渐变的线性；
    private LinearGradient mLinearGradient;

    public AudioBar(Context context) {
        this(context, null);
    }

    public AudioBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AudioBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
        mPaint.setStyle(Paint.Style.FILL);

        mRectCount = 12;
    }

    /**
     * double random = Math.random();
     * // >=0.0 <1.0
     * Logger.d("产生随机数: " + random);
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Logger.d("AudioBar onSizeChanged");
        mWidth = getWidth();
        mRectWidth = mWidth / mRectCount;  // 计算每一个音频条的宽度
        mRectHeight = getHeight();   // 音频条高度为屏幕高度

        /**
         *  @param x0渐变线起点的x坐标
         *  @param y0渐变线起点的y坐标
         *  @param x1渐变线终点的x坐标
         *  @param y1渐变线末端的y坐标
         *  @param color0渐变线开始处的颜色。
         *  @param color1渐变线末端的颜色。
         *  @param tile着色器平铺模式
         *
         *    (x0,y0)：渐变起始点坐标
         *    (x1,y1):渐变结束点坐标
         *    color0:渐变开始点颜色,16进制的颜色表示，必须要带有透明度
         *    color1:渐变结束颜色
         *
         */
        mLinearGradient = new LinearGradient(0, 0, mRectWidth, mRectHeight,
                Color.BLUE, Color.GREEN, Shader.TileMode.CLAMP);

        // 设置着色器
        mPaint.setShader(mLinearGradient);


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < mRectCount; i++) {
            mRandom = Math.random();
            canvas.drawRect(mRectWidth * i + offset, (float) (mRectHeight * mRandom),
                    mRectWidth * (i + 1),
                    mRectHeight, mPaint);
        }

        // 间隔300ms刷新onDraw方法, 让其“动起来”
        postInvalidateDelayed(300);
    }
}
