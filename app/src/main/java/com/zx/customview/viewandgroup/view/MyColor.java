package com.zx.customview.viewandgroup.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class MyColor extends View {

    private Paint mPaint;

    public MyColor(Context context) {
        this(context, null);
    }

    public MyColor(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyColor(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.BLUE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(3);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(20);

        /*
         * (x0,y0)：渐变起始点坐标
         * (x1,y1):渐变结束点坐标
         * color0:渐变开始点颜色,16进制的颜色表示，必须要带有透明度
         * color1:渐变结束颜色
         */
        LinearGradient linearGradient = new LinearGradient(
                0, 0, getWidth(), 400, Color.RED,
                Color.GREEN, Shader.TileMode.CLAMP);
        mPaint.setShader(linearGradient);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0, 0, getWidth(), 400, mPaint);

    }
}
