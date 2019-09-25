package com.example.customview.step2.path.gestures;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by jinhui on 2018/1/25.
 * Email:1004260403@qq.com
 * <p>
 * 1、实现方式一：Path.lineTo(x,y)
 *
 * /**
 * 三、手指轨迹

 要实现手指轨迹其实是非常简单的，我们只需要在自定义中拦截OnTouchEvent，然后根据手指的移动轨迹来绘制Path即可。
 要实现把手指的移动轨迹连接起来，最简单的方法就是直接使用Path.lineTo()就能实现把各个点连接起来。
 1、实现方式一：Path.lineTo(x,y)
 *
 */

public class Gestures1View extends View {

    private Path mPath = new Path();

    public Gestures1View(Context context) {
        super(context);
    }

    public Gestures1View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Gestures1View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        canvas.drawPath(mPath, paint);
    }

    /**
     * 当用户点击屏幕的时候，我们调用mPath.moveTo(event.getX(), event.getY());然后在用户移动手指时使用mPath.lineTo(event.getX(), event.getY());将各个点串起来。然后调用postInvalidate()重绘；
     Path.moveTo()和Path.lineTo()的用法，大家如果看了《android Graphics（二）：路径及文字》之后，理解起来应该没什么难度，但这里有两个地方需要注意
     第一：有关在case MotionEvent.ACTION_DOWN时return true的问题：return true表示当前控件已经消费了下按动作，之后的ACTION_MOVE、ACTION_UP动作也会继续传递到当前控件中；如果我们在case MotionEvent.ACTION_DOWN时return false，那么后序的ACTION_MOVE、ACTION_UP动作就不会再传到这个控件来了。有关动作拦截的知识，后续会在这个系列中单独来讲，大家先期待下吧。
     第二：这里重绘控件使用的是postInvalidate();而我们以前也有用Invalidate()函数的。这两个函数的作用都是用来重绘控件的，但区别是Invalidate()一定要在UI线程执行，如果不是在UI线程就会报错。而postInvalidate()则没有那么多讲究，它可以在任何线程中执行，而不必一定要是主线程。其实在postInvalidate()就是利用handler给主线程发送刷新界面的消息来实现的，所以它是可以在任何线程中执行，而不会出错。而正是因为它是通过发消息来实现的，所以它的界面刷新可能没有直接调Invalidate()刷的那么快。
     所以在我们确定当前线程是主线程的情况下，还是以invalide()函数为主。当我们不确定当前要刷新页面的位置所处的线程是不是主线程的时候，还是用postInvalidate为好；
     这里我是故意用的postInvalidate()，因为onTouchEvent()本来就是在主线程中的，使用Invalidate()是更合适的。当我们
     有关OnDraw函数就没什么好讲的，就是把path给画出来：

     我们把S放大，明显看出，在两个点连接处有明显的转折，而且在S顶部位置横纵坐标变化比较快的位置，看起来跟图片这大后的马赛克一样；利用Path绘图，是不可能出现马赛克的，因为除了Bitmap以外的任何canvas绘图全部都是矢量图，也就是利用数学公式来作出来的图，无论放在多大屏幕上，都不可能会出现马赛克！这里利用Path绘图，在S顶部之所以看起来像是马赛克是因为这个S是由各个不同点之间连线写出来的，而之间并没有平滑过渡，所以当坐标变化比较剧烈时，线与线之间的转折就显得特别明显了。
     所以要想优化这种效果，就得实现线与线之间的平滑过渡，很显然，二阶贝赛尔曲线就是干这个事的。下面我们就利用我们新学的Path.quadTo函数来重新实现下移动轨迹效果。

     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(event.getX(), event.getY());
                return true;
            case MotionEvent.ACTION_MOVE:
                mPath.lineTo(event.getX(), event.getY());
                postInvalidate();
                break;
        }
        return super.onTouchEvent(event);
    }

    public void reset(){
        mPath.reset();
        invalidate();
    }
}
