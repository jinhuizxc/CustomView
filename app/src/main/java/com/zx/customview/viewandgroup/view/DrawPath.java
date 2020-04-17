package com.zx.customview.viewandgroup.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class DrawPath extends View {

    private Paint mPaint1, mPaint2;

    public DrawPath(Context context) {
        super(context);
        initView();
    }

    public DrawPath(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public DrawPath(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        mPaint1 = new Paint();
        mPaint1.setColor(Color.GRAY);
        mPaint1.setStyle(Paint.Style.STROKE);
        mPaint1.setAntiAlias(true);

        mPaint2 = new Paint();
        mPaint2.setColor(Color.RED);
        mPaint2.setStyle(Paint.Style.STROKE);
        mPaint2.setAntiAlias(true);
        mPaint2.setStrokeWidth(5);
    }

    /**
     * path lineTo moveTo
     * android Path类中的moveTo和lineTo的区别
     * https://blog.csdn.net/lan12334321234/article/details/70049539
     * 1、moveTo
     * moveTo 不会进行绘制，只用于移动移动画笔。
     * 结合以下方法进行使用。
     * <p>
     * 2、lineTo
     * lineTo 用于进行直线绘制。
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int mWidth = getWidth();
        int mHeight = getHeight();

        /*canvas.translate(mWidth / 2, mHeight / 2);  //坐标移动到屏幕中心
        Path path = new Path();       //创建Path
        path.lineTo(200, 200);
        path.lineTo(200, -200);
        canvas.drawPath(path, mPaint2);  //绘制Path*/

        /*canvas.translate(mWidth / 2, mHeight / 2);
        Path path = new Path();
        path.lineTo(200, 200);
        path.moveTo(200, 0);
        path.lineTo(200, -200);
        canvas.drawPath(path, mPaint2);*/

        /*canvas.translate(mWidth / 2, mHeight / 2);
        Path path = new Path();
        path.lineTo(200, 200);
        canvas.drawPoint(200, 200, mPaint2);
        // 设置终点setLastPoint
        // 重置当前path中最后一个点位置，如果在绘制之前调用，效果和moveTo相同
        path.setLastPoint(200, 100);
        path.lineTo(200, -200);
        canvas.drawPath(path, mPaint2);*/


        /*canvas.translate(mWidth / 2, mHeight / 2);
        Path path = new Path();
        path.lineTo(200, 200);
        path.lineTo(200, -200);
        path.close();  // 将path首尾进行连接
        canvas.drawPath(path, mPaint2);*/

        // path.addRect添加矩形
       /* canvas.translate(mWidth / 2, mHeight / 2);
        Path path = new Path();
        path.addRect(-200, -200, 200, 200, Path.Direction.CW);
        path.setLastPoint(-300, 300); // 最后一个点在左下，可以推测，绘制的方向为左上右下，顺时针绘制
        canvas.drawPath(path, mPaint2);*/


        // addPath
       /* canvas.translate(mWidth / 2, mHeight / 2);
        Path path = new Path();
        Path src = new Path();
        path.addRect(-200, -200, 200, 200, Path.Direction.CW);
        src.addCircle(0, 0, 100, Path.Direction.CW);
        path.addPath(src, 0, -100);
        path.addPath(src, 0, 0);
        path.addPath(src, 0, -200);
        canvas.drawPath(path, mPaint2);*/

        // addArc 弧度
        /*canvas.translate(mWidth / 2, mHeight / 2);
        Path path = new Path();
        path.lineTo(100, 100);
        RectF oval = new RectF(0, 0, 300, 300);
        canvas.drawRect(oval, mPaint2);
        path.addArc(oval, 0, 270);
        canvas.drawPath(path, mPaint2);*/

        // arcTo 该方法是画一个弧线的路径.
      /*  canvas.translate(mWidth / 2, mHeight / 2);
        Path path = new Path();
        path.lineTo(100, 100);
        RectF oval = new RectF(0, 0, 300, 300);
        path.arcTo(oval, 0, 270);
        canvas.drawPath(path, mPaint2);*/

        // offset
        canvas.translate(mWidth / 2, mHeight / 2);
        Path path = new Path();
        path.addCircle(0, 0, 100, Path.Direction.CW);
        Path dst = new Path();
        path.offset(300, 0, dst);
        canvas.drawPath(path, mPaint2);
        path.offset(300, 0);
        canvas.drawPath(path, mPaint2);
    }
}
