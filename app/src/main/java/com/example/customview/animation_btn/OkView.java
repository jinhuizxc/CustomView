package com.example.customview.animation_btn;


import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * 打钩动画
 */
public class OkView extends View {

    /**
     * 对勾（√）画笔
     */
    private Paint okPaint;

    /**
     * view的宽度
     */
    private int width;
    /**
     * view的高度
     */
    private int height;
    /**
     * 路径--用来获取对勾的路径
     */
    private Path path = new Path();

    private int default_two_circle_distance;

    /**
     * 取路径的长度
     */
    private PathMeasure pathMeasure;
    /**
     * 对路径处理实现绘制动画效果
     */
    private PathEffect pathEffect;
    /**
     * 绘制对勾（√）的动画
     */
    private ValueAnimator animator_draw_ok;
    /**
     * 动画执行时间
     */
    private int duration = 1000;
    /**
     * 是否开始绘制对勾
     */
    private boolean isStartDrawOk = false;

    public OkView(Context context) {
        this(context, null);
    }

    public OkView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OkView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    private void  initPaint(){
        okPaint = new Paint();
        okPaint.setStrokeWidth(10);
        okPaint.setStyle(Paint.Style.STROKE);
        okPaint.setAntiAlias(true);
        okPaint.setColor(Color.BLUE);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        // view的宽度、高度
        width = w;
        height = h;
        default_two_circle_distance = (width - height) / 2;

        initOk();

        set_draw_ok_animation();

    }

    private void set_draw_ok_animation() {
        animator_draw_ok = ValueAnimator.ofFloat(1, 0);
        animator_draw_ok.setDuration(duration);
        animator_draw_ok.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                isStartDrawOk = true;
                float value = (float) animation.getAnimatedValue();

                pathEffect = new DashPathEffect(new float[]{pathMeasure.getLength(), pathMeasure.getLength()}, value * pathMeasure.getLength());
                okPaint.setPathEffect(pathEffect);
                invalidate();

            }
        });
    }

    /**
     * 绘制对勾
     */
    private void initOk() {
        //对勾的路径
        path.moveTo(default_two_circle_distance + height / 8 * 3, height / 2);
        path.lineTo(default_two_circle_distance + height / 2, height / 5 * 3);
        path.lineTo(default_two_circle_distance + height / 3 * 2, height / 5 * 2);

        pathMeasure = new PathMeasure(path, true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        if (isStartDrawOk) {
            canvas.drawPath(path, okPaint);
        }

    }

    public void start() {
        animator_draw_ok.start();
    }
}
