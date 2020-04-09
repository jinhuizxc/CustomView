package com.zx.customview.widget.loadingview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 自定义View-29 仿直播圆点加载效果
 * https://www.jianshu.com/p/babc9a5faf4b
 * <p>
 * 实现思路
 * 2.1 默认状态三个圆点重叠.
 * 2.2 一个圆点往左移动,再往右回到重叠点,另外一个圆点往右移动,再往左回到重叠点.还有一个圆点在中间不动
 * 2.3 等回到重叠点改变颜色.往左边的颜色给中间,中间给右边.右边给左边.
 *
 * 绘制圆
 */
public class CircleView extends View {

    private Paint mCirclePaint = null;
    private int mColor;

    public CircleView(Context context) {
        this(context, null);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CircleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        init();
    }

    private void init() {
        mCirclePaint = new Paint();
        mCirclePaint.setAntiAlias(true);  // 抗锯齿
        mCirclePaint.setDither(true);  // 设置防抖动
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int cx = getWidth() / 2;
        int cy = getHeight() / 2;
        canvas.drawCircle(cx, cy, cx, mCirclePaint);
    }

    public void exChangeColor(int color){
        this.mColor = color;
        mCirclePaint.setColor(mColor);
        invalidate();
    }

    public int getColor() {
        return mColor;
    }
}
