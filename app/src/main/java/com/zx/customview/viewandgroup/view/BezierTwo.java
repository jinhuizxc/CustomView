package com.zx.customview.viewandgroup.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 方法调用：
 * onMeasure:
 * onMeasure:
 * onSizeChanged:
 * onLayout:
 * onDraw:
 * onDraw:
 */
public class BezierTwo extends View {

    private static final String TAG = "BezierTwo";
    private Paint mPaint;
    private int centerX, centerY;
    private PointF start, end, control; // 起点，结束点，控制点

    public BezierTwo(Context context) {
        super(context);
        init();
    }

    public BezierTwo(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public BezierTwo(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(8);

        //初始化起点，结束点，控制点
        start = new PointF(0, 0);
        end = new PointF(0, 0);
        control = new PointF(0, 0);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure: ");
    }

    /**
     * 在onMeasure与onLayout方法之间进行调用;
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "onSizeChanged: " + w + " , " + h + " , " + oldw + " , " + oldh);
        //  onSizeChanged: 720 , 617 , 0 , 0
        centerX = w / 2;
        centerY = h / 2;

        //初始化数据点和控制点
        start.x = centerX - 200;
        start.y = centerY;
        end.x = centerX + 200;
        end.y = centerY;
        control.x = centerX;
        control.y = centerY - 100;

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        Log.d(TAG, "onLayout: ");
    }

    /**
     * MotionEvent提供的方法：
     * getX():获取点击事件距离控件左边的距离，即视图坐标
     * getY():获取点击事件距离控件东边的距离，即视图坐标
     * getRawX():获取点击事件距离整个屏幕左边的距离，即绝对坐标
     * getRawY():获取点击事件距离整个屏幕顶边的距离，即绝对坐标
     *
     * onTouchEvent：当前的 view 把事件拦截了，事件会传递到这个方法中；
     * 有3种返回情况：
     * return true：消费了该事件，事件到此结束；
     * return false：没有消费事件，事件会以冒泡方式传递到 最上层的 view 或者 activity，如果最上边的 view 或者 activity没有处理，还是 返回 false，该事件将消失。接下来的所有事件都会被 最上层的view 的 onTouchEvent捕获；
     * return super.onTouchEvent(event)：默认情况，和 return false一样；
     *
     *
     * 测试发现   return super.onTouchEvent(event); 只是会触发一次点击重绘onDraw一次，
     * 但是将  return true;后可以不断绘制，不断的调用onDraw进行绘制;
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        //根据触摸点更新控制点，并重绘
        control.x = event.getX();
        control.y = event.getY();
        invalidate();
        return true;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw: ");

        //绘制数据点和控制点
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(20);
        canvas.drawPoint(start.x, start.y, mPaint);
        canvas.drawPoint(end.x, end.y, mPaint);
        canvas.drawPoint(control.x, control.y, mPaint);

        //绘制辅助线
        mPaint.setStrokeWidth(4);
        canvas.drawLine(start.x, start.y, control.x, control.y, mPaint);
        canvas.drawLine(end.x, end.y, control.x, control.y, mPaint);

        //绘制贝赛尔曲线
        mPaint.setStrokeWidth(8);
        mPaint.setColor(Color.RED);

        Path path = new Path();
        path.moveTo(start.x,start.y);
        path.quadTo(control.x, control.y, end.x, end.y);
        canvas.drawPath(path, mPaint);

    }
}
