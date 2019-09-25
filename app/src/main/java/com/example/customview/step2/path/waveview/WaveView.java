package com.example.customview.step2.path.waveview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by jinhui on 2018/1/25.
 * Email:1004260403@qq.com
 * <p>
 * 实现波浪效果
 * <p>
 * 1、实现全屏波纹
 * 上面我们已经能够实现一个波形，只要我们再多实现几个波形，就可以覆盖整个屏幕了。
 */

public class WaveView extends View {

    private Paint mPaint;
    private Path mPath;
    private int mItemWaveLength = 400;

    private int dx;
    private int dy;
    public WaveView(Context context) {
        super(context);

    }

    public WaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setColor(Color.GREEN);
        mPaint.setStrokeWidth(5);
//        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public WaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 1.我们将mPath的起始位置向左移一个波长：
     * mPath.moveTo(-mItemWaveLength,originY);
     * <p>
     * 2.然后利用for循环画出当前屏幕中可能容得下的所有波：
     * <p>
     * for (int i = -mItemWaveLength;i<=getWidth()+mItemWaveLength;i+=mItemWaveLength){
     * mPath.rQuadTo(halfWaveLen/2,-100,halfWaveLen,0);
     * mPath.rQuadTo(halfWaveLen/2,100,halfWaveLen,0);
     * }
     * <p>
     * mPath.rQuadTo(halfWaveLen/2,-100,halfWaveLen,0);画的是一个波长中的前半个波，mPath.rQuadTo(halfWaveLen/2,100,halfWaveLen,0);画的是一个波长中的后半个波。大家在这里可以看到，屏幕左右都多画了一个波长的图形。这是为了波形移动做准备的。
     * 到这里，我们是已经能画出来一整屏幕的波形了，下面我们把整体波形闭合起来
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        int originY = 300;
        int halfWaveLen = mItemWaveLength / 2;
//        mPath.moveTo(-mItemWaveLength, originY);
        //博客中代码,不向下移动
        mPath.moveTo(-mItemWaveLength + dx, originY);

        //实现向下移动动画,加上这两行代码
        mPath.moveTo(-mItemWaveLength + dx, originY + dy);
        dy += 1;

        for (int i = -mItemWaveLength; i <= getWidth() + mItemWaveLength; i += mItemWaveLength) {
            mPath.rQuadTo(halfWaveLen / 2, -100, halfWaveLen, 0);
            mPath.rQuadTo(halfWaveLen / 2, 100, halfWaveLen, 0);
        }

        // 这段代码相比上面的代码，增加了两部分内容：
        // 第一，将paint设置为填充：mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        // 第二，将path闭合：
        mPath.lineTo(getWidth(), getHeight());
        mPath.lineTo(0, getHeight());
        mPath.close();

        canvas.drawPath(mPath, mPaint);
    }

    /**
     * 2、实现移动动画
     * 动画的长度为一个波长，将当前值保存在类的成员变量dx中；
     * 然后在画图的时候，在path.moveTo（）中加上现在的移动值dx:mPath.moveTo(-mItemWaveLength+dx,originY);
     * 完整的绘图代码如下：
     */
    public void startAnim() {
        ValueAnimator animator = ValueAnimator.ofInt(0, mItemWaveLength);
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }

    /**
     * 如果把波长设置为1000，就可以实现本段开篇的动画了。
     如果想让波纹像开篇时那要同时向下移动，大家只需要在path.moveTo(x,y)的时候，
     通过动画同时移动y坐标就可以了，代码比较简单，而且本文实在是太长了，
     具体实现就不再讲了，大家可以在源码中加以尝试。
     */
}
