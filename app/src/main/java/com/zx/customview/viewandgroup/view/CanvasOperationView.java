package com.zx.customview.viewandgroup.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.orhanobut.logger.Logger;

public class CanvasOperationView extends View {

    private Paint mPaint1, mPaint2;

    public CanvasOperationView(Context context) {
        super(context);
        initView();
    }

    public CanvasOperationView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CanvasOperationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint1 = new Paint();
        mPaint1.setColor(Color.BLUE);
        mPaint1.setStyle(Paint.Style.STROKE);

        mPaint2 = new Paint();
        mPaint2.setColor(Color.RED);
        mPaint2.setStyle(Paint.Style.FILL);
        mPaint2.setStrokeWidth(10);

    }

    /**
     * 对于画布平移需要学习Android坐标系以及视图坐标系，了解基本概念
     * https://blog.csdn.net/Jsagacity/article/details/78542314
     * <p>
     * 这些方法可以分成如下两个类别：
     * View提供的获取坐标方法：
     * getTop():获取到的是View自身的顶边到其父布局顶边的距离
     * getLeft():获取到的是View自身的左边到其父布局左边的距离
     * getRight():获取到的是View自身的右边到其父布局左边的距离
     * getBottom():获取到的是View自身的底边到其父布局顶边的距离
     * <p>
     * 另外View获取自身宽高
     * getHeight()：获取View自身高度
     * getWidth()：获取View自身宽度
     * <p>
     * MotionEvent提供的方法：
     * getX():获取点击事件距离控件左边的距离，即视图坐标
     * getY():获取点击事件距离控件东边的距离，即视图坐标
     * getRawX():获取点击事件距离整个屏幕左边的距离，即绝对坐标
     * getRawY():获取点击事件距离整个屏幕顶边的距离，即绝对坐标
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // translate
       /* canvas.drawCircle(60, 60, 50, mPaint2);
        // 画布平移, 画同一个圆
        canvas.translate(200, 200);
        canvas.drawCircle(60, 60, 50, mPaint2);

        int top = getTop();
        int bottom = getBottom();
        Logger.d("onDraw: " + top + " , " + bottom);*/
        // onDraw: 0 , 1299

        // scale
//        int width = getWidth();
//        int height = getHeight();
//        Logger.d("onDraw 自身宽高: " + width + " , " + height);
//        // 自身宽高: 720 , 1299
//        canvas.translate(width / 2, height / 2);  //将坐标移到中心
//        RectF rectF = new RectF(0, -400, 400, 0);
//        canvas.drawRect(rectF, mPaint1);
//        canvas.scale(0.5f, 0.5f);
//        canvas.drawRect(rectF, mPaint1);


        /*int width = getWidth();
        int height = getHeight();
        canvas.translate(width / 2, height / 2);  //将坐标移到中心
        RectF rectF = new RectF(-400, -400, 400, 400);
        canvas.drawRect(rectF, mPaint1);
        for (int i = 0; i < 20; i++) {
            canvas.scale(0.9f, 0.9f);
            canvas.drawRect(rectF, mPaint1);
        }*/

        // rotate 正数正向旋转
       /* int width = getWidth();
        int height = getHeight();
        canvas.translate(width / 2, height / 2);  //将坐标移到中心
        RectF rectF = new RectF(0, -400, 400, 0);
        canvas.drawRect(rectF, mPaint1);
        canvas.rotate(180);
        canvas.drawRect(rectF, mPaint1);*/

        /*int width = getWidth();
        int height = getHeight();
        canvas.translate(width / 2, height / 2);  //将坐标移到中心
        RectF rectF = new RectF(0, -400, 400, 0);
        canvas.drawRect(rectF, mPaint1);
        canvas.rotate(180, 200, 0);    // 用指定的旋转值预先连接当前矩阵。
        canvas.drawRect(rectF, mPaint1);*/


       /* int width = getWidth();
        int height = getHeight();
        canvas.translate(width / 2, height / 2);  //将坐标移到中心
        RectF rectF = new RectF(0, -400, 400, 0);
        canvas.drawRect(rectF, mPaint1);
        canvas.rotate(180);
        canvas.rotate(50);
        canvas.drawRect(rectF, mPaint1);*/


        int width = getWidth();
        int height = getHeight();
        canvas.translate(width / 2, height / 2);  //将坐标移到中心
        RectF rectF = new RectF(0, 0, 200, 200);
        canvas.drawRect(rectF, mPaint1);
        canvas.skew(1, 0);  //水平斜切45
        canvas.drawRect(rectF, mPaint1);

    }
}
