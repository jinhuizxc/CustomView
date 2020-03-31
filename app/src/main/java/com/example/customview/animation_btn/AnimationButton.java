package com.example.customview.animation_btn;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.Rect;
import android.graphics.RectF;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Android自定义动画酷炫的提交按钮
 * https://www.jianshu.com/p/3eb9777f6ab7
 *
 *
 * D/AnimationButton: onSizeChanged: 600 , 150
 *  D/AnimationButton: onDraw:
 *
 *  执行顺序：
 *  onSizeChanged: 执行一次;
 *  onSizeChanged 方法 >  onDraw
 *
 */
public class AnimationButton extends View {

    private static final String TAG = "AnimationButton";
    /**
     * view的宽度
     */
    private int width;
    /**
     * view的高度
     */
    private int height;
    /**
     * 圆角半径
     */
    private int circleAngle;
    /**
     * 默认两圆圆心之间的距离=需要移动的距离
     */
    private int default_two_circle_distance;
    /**
     * 两圆圆心之间的距离
     */
    private int two_circle_distance;
    /**
     * 背景颜色
     */
    private int bg_color = 0xffbc7d53;
    /**
     * 按钮文字字符串
     */
    private String buttonString = "确认完成";
    /**
     * 动画执行时间
     */
    private int duration = 1000;
    /**
     * view向上移动距离
     */
    private int move_distance = 300;

    /**
     * 圆角矩形画笔
     */
    private Paint paint;
    /**
     * 文字画笔
     */
    private Paint textPaint;
    /**
     * 对勾（√）画笔
     */
    private Paint okPaint;
    /**
     * 文字绘制所在矩形
     */
    private Rect textRect = new Rect();
    /**
     * 动画集
     */
    private AnimatorSet animatorSet = new AnimatorSet();
    /**
     * 矩形到圆角矩形过渡的动画
     */
    private ValueAnimator animator_rect_to_angle;
    /**
     * 矩形到正方形过度的动画
     */
    private ValueAnimator animator_rect_to_square;
    /**
     * view上移的动画
     */
    private ObjectAnimator animator_move_top;
    /**
     * 绘制对勾（√）的动画
     */
    private ValueAnimator animator_draw_ok;
    /**
     * 是否开始绘制对勾
     */
    private boolean isStartDrawOk = false;
    /**
     * 根据view的大小设置成矩形
     */
    private RectF rectF = new RectF();
    /**
     * 路径--用来获取对勾的路径
     */
    private Path path = new Path();
    /**
     * 取路径的长度
     */
    private PathMeasure pathMeasure;
    /**
     * 对路径处理实现绘制动画效果
     */
    private PathEffect pathEffect;
    /**
     * 点击事件及动画事件完成回调
     */
    private AnimationButtonListener animationButtonListener;

    public void setAnimationButtonListener(AnimationButtonListener animationButtonListener) {
        this.animationButtonListener = animationButtonListener;
    }

    /**
     * 启动动画
     */
    public void start() {
        animatorSet.start();
    }

    /**
     * 动画还原
     */
    public void reset() {
        isStartDrawOk = false;
        circleAngle = 0;
        two_circle_distance = 0;
        default_two_circle_distance = (width - height) / 2;
        textPaint.setAlpha(255);
        setTranslationY(getTranslationY() + move_distance);
        invalidate();
    }

    /**
     * 接口回调
     */
    public interface AnimationButtonListener{
        /**
         * 按钮点击监听
         */
        void onClickListener();
        /**
         * 动画完成回调
         */
        void animationFinish();
    }




    public AnimationButton(Context context) {
        this(context, null);
    }

    public AnimationButton(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AnimationButton(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        initPaint();

        // 设置监听
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (animationButtonListener != null){
                    animationButtonListener.onClickListener();
                }
            }
        });

        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (animationButtonListener != null){
                    animationButtonListener.animationFinish();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }

    private void initPaint() {
        paint = new Paint();
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(bg_color);

        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(40);
        textPaint.setColor(Color.WHITE);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setAntiAlias(true);

        okPaint = new Paint();
        okPaint.setStrokeWidth(10);
        okPaint.setStyle(Paint.Style.STROKE);
        okPaint.setAntiAlias(true);
        okPaint.setColor(Color.WHITE);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // view的宽度、高度
        width = w;
        height = h;
        Log.d(TAG, "onSizeChanged: " + width + " , " + height);
        // D/AnimationButton: onSizeChanged: 600 , 150
        default_two_circle_distance = (w - h) / 2;
        Log.d(TAG, "default_two_circle_distance: " + default_two_circle_distance);
        // 225

        initOk();
        initAnimation();

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw: ");

        draw_oval_to_circle(canvas);
        // 绘制文字
        drawText(canvas);

        if (isStartDrawOk) {
            canvas.drawPath(path, okPaint);
        }

    }

    /**
     * 绘制文字
     *
     * @param canvas 画布
     */
    private void drawText(Canvas canvas) {
        // 绘制矩形所在区域
        textRect.left = 0;
        textRect.top = 0;
        textRect.right = width;
        textRect.bottom = height;

        // 绘制中间线所在高度
        // 计算Baseline绘制的Y坐标 ，计算方式：画布高度的一半 - 文字总高度的一半
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        int baseline = (textRect.bottom + textRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        // textRect.centerX(): 矩形的水平中心
        canvas.drawText(buttonString, textRect.centerX(), baseline, textPaint);
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

    /**
     * 初始化所有动画
     */
    private void initAnimation() {
        set_rect_to_angle_animation();
        set_rect_to_circle_animation();
        set_move_to_up_animation();
        set_draw_ok_animation();

        // animator_rect_to_angle: 直角矩形变椭圆
        animatorSet
                .play(animator_move_top)
                .before(animator_draw_ok)
                .after(animator_rect_to_square)
                .after(animator_rect_to_angle);

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
     * 设置view上移的动画
     * getTranslationY: 获取
     */
    private void set_move_to_up_animation() {
        float translationY = this.getTranslationY();
        Log.d(TAG, "translationY: " + translationY); // translationY: 0.0
        animator_move_top = ObjectAnimator
                .ofFloat(this, "translationY",
                        translationY, translationY - move_distance);
      /*  animator_move_top = ObjectAnimator
                .ofFloat(this, "translationY",
                        0,  - move_distance);*/
        animator_move_top.setDuration(duration);
        animator_move_top.setInterpolator(new AccelerateDecelerateInterpolator());

    }

    /**
     * 设置圆角矩形过度到圆的动画
     */
    private void set_rect_to_circle_animation() {
        animator_rect_to_square = ValueAnimator.ofInt(0, default_two_circle_distance);
        animator_rect_to_square.setDuration(duration);
        animator_rect_to_square.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                two_circle_distance = (int) animation.getAnimatedValue();
                Log.d(TAG, "two_circle_distance: " + two_circle_distance);
                // 透明度计算
                int alpha = 255 - (two_circle_distance * 255) / default_two_circle_distance;
                Log.d(TAG, "alpha: " + alpha);
                textPaint.setAlpha(alpha);
                invalidate();
            }
        });
    }

    /**
     * 设置矩形过渡到圆角矩形的动画
     */
    private void set_rect_to_angle_animation() {
        // 构造并返回一个在两个int值之间进行动画处理的ValueAnimator
        animator_rect_to_angle = ValueAnimator.ofInt(0, height / 2);
        animator_rect_to_angle.setDuration(duration);
        animator_rect_to_angle.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                circleAngle = (int) animation.getAnimatedValue();
                // 值慢慢变大
                invalidate(); // 刷新ondraw()
            }
        });
    }



    /**
     * 绘制长方形变成圆形
     * @param canvas
     */
    private void draw_oval_to_circle(Canvas canvas) {
        // 画圆角矩形（左上右下）
        rectF.left = two_circle_distance;
        rectF.top = 0;
        rectF.right = width - two_circle_distance;
        rectF.bottom = height;
        // 椭圆矩形， 圆心半径，画笔
        canvas.drawRoundRect(rectF, circleAngle, circleAngle, paint);

    }


}
