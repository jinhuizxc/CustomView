package com.example.customview.step2.path.gestures;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by jinhui on 2018/1/25.
 * Email:1004260403@qq.com
 * <p>
 * 实现方式二（优化）：使用Path.quadTo()函数实现过渡
 * (1)、原理概述
 * <p>
 * 我们上面讲了，使用Path.lineTo()的最大问题就是线段转折处不够平滑。Path.quadTo()可以实现平滑过渡，但使用Path.quadTo()的最大问题是，如何找到起始点和结束点。
 * 下图中，有用绿点表示的三个点，连成的两条直线，很明显他们转折处是有明显折痕的
 */

public class Gestures2View extends View {

    private Path mPath = new Path();
    private float mPreX, mPreY;

    public Gestures2View(Context context) {
        super(context);
    }

    public Gestures2View(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public Gestures2View(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 在ACTION_DOWN的时候，
     * 利用 mPath.moveTo(event.getX(),event.getY())将Path的初始位置设置到手指的触点处，如果不调用mPath.moveTo的话，会默认是从(0,0)开始的。然后我们定义两个变量mPreX，mPreY来表示手指的前一个点。我们通过上面的分析知道，这个点是用来做控制点的。最后return true让ACTION_MOVE,ACTION_UP事件继续向这个控件传递。
     * 在ACTION_MOVE时：
     * <p>
     * 我们先找到结束点，我们说了结束点是这个线段的中间位置，所以很容易求出它的坐标endX,endY；控制点是上一个手指位置即mPreX,mPreY;那有些同学可能会问了，那起始点是哪啊。在开篇讲quadTo()函数时，就已经说过，第一个起始点是Path.moveTo(x,y)定义的，其它部分，一个quadTo的终点，是下一个quadTo的起始点。
     * 所以这里的起始点，就是上一个线段的中间点。所以，这样就与钢笔工具绘制过程完全对上了：把各个线段的中间点做为起始点和终点，把终点前一个手指位置做为控制点。
     * 后面的onDraw()和reset()函数就没什么难度了，上面的例子中也讲过了，就不再赘述了
     * 最终的效果图如下：
     * 从效果图中可以明显可以看出，通过quadTo实现的曲线更顺滑
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mPath.moveTo(event.getX(), event.getY());
                mPreX = event.getX();
                mPreY = event.getY();
                return true;
            case MotionEvent.ACTION_MOVE:
                float endX = (mPreX + event.getX()) / 2;
                float endY = (mPreY + event.getY()) / 2;
                mPath.quadTo(mPreX, mPreY, endX, endY);
                mPreX = event.getX();
                mPreY = event.getY();
                invalidate();
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GREEN);
        paint.setStrokeWidth(5);

        canvas.drawPath(mPath,paint);
    }

    public void reset() {
        mPath.reset();
        postInvalidate();
    }
}
