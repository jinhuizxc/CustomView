package com.example.customview.step1.view;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.BounceInterpolator;

import com.example.customview.step1.customevaluator.PointEvaluator;
import com.example.customview.step1.model.Point;


/**
 * Created by jinhui on 2018/1/5.
 * Email:1004260403@qq.com
 */

public class MyPointView extends View {

    private Point mCurPoint;
    Paint paint;

    Point mPoint = new Point(100);

    public MyPointView(Context context) {
        super(context);
    }

    public MyPointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyPointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        if(mCurPoint != null){
//            paint = new Paint();
//            paint.setAntiAlias(true);
//            paint.setColor(Color.RED);
//            paint.setStyle(Paint.Style.FILL);
//            canvas.drawCircle(300, 300, mCurPoint.getRadius(), paint);
//        }
        if (mPoint != null){
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawCircle(300,300,mPoint.getRadius(),paint);
        }


    }

    public void doPointAnim() {
        ValueAnimator animator = ValueAnimator.ofObject(new PointEvaluator(), new Point(20), new Point(200));
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mCurPoint = (Point) animation.getAnimatedValue();
                invalidate();  // 调用ondraw()方法
            }
        });
        animator.setDuration(1000);
        animator.setInterpolator(new BounceInterpolator());
        animator.start();
    }

    /**
     *
     * 从动画中可以看出，半径已经不是从0开始的了，而是从50开始的。
     // 最后我们总结一下：当且仅当动画的只有一个过渡值时，系统才会调用对应属性的get函数来得到动画的初始值。
     * @return
     */ 
    public int getPointRadius(){
        return 50;
    }
    // 添加这个set方法
    void setPointRadius(int radius){

        mPoint.setRadius(radius);
        invalidate();
    }
}
