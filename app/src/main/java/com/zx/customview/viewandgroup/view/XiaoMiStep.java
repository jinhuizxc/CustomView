package com.zx.customview.viewandgroup.view;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.orhanobut.logger.Logger;
import com.zx.customview.R;

import java.text.DecimalFormat;

/**
 * 小米计步效果
 * <p>
 * # 自定义View（三），仿小米运动计步
 * https://www.jianshu.com/p/59a1fda46be1
 * # Android 那些事– 小米手环 测量心率 动画实现
 * https://blog.csdn.net/xueerfei008/article/details/49998931?depth_1-utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-2&utm_source=distribute.pc_relevant.none-task-blog-BlogCommendFromMachineLearnPai2-2
 */
public class XiaoMiStep extends View {

    private Paint mPaint;
    private Paint arcPaint; // 圆弧
    private Paint textPaint;  // 文字画笔
    private Paint pointPaint;

    //背景的坐标
    private int widthBg, heightBg;
    private int radius_out_circle; //最外层圆的半径
    private int radius_inner_circle; //内层圆的半径
    private int line_length; //线的长度

    // 定义背景色
    private int background_color, out_circle_color, out_dot_color,
            line_color, ring_color, step_num_color, other_text_color;
    //动画效果的添加
    private AnimatorSet animatorSet;

    private int angle;  //角度
    private int currentFootNumPre; //当前步数进度
    private int currentFootNum; //当前步数
    private float currentDistance; //当前公里
    private int myFootNum; //我的步数
    private int currentCal; //当前卡路里，单位千卡

    public XiaoMiStep(Context context) {
        this(context, null);
    }

    public XiaoMiStep(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XiaoMiStep(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        //获得atts.xml定义的属性值，存储在TypedArray中
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.XiaoMiStep);
        // 要想获取到getIndexCount的值需要在xml中设置对应的自定义属性
//        TypedArray typedArray = context.getTheme().obtainStyledAttributes(attrs, R.styleable.XiaoMiStep, defStyleAttr, 0);
        int indexCount = typedArray.getIndexCount();
        Logger.d("indexCount: " + indexCount);

        for (int i = 0; i < indexCount; i++) {
            int index = typedArray.getIndex(i);
            switch (index) {
                case R.styleable.XiaoMiStep_backGroundColor: //背景颜色
                    background_color = typedArray.getColor(index, Color.WHITE);  //默认为白色
                    break;
                case R.styleable.XiaoMiStep_out_circle_color: //最外侧圆
                    out_circle_color = typedArray.getColor(index, Color.WHITE);
                    break;
                case R.styleable.XiaoMiStep_out_dot_color: //最外侧圆上的小圆点
                    out_dot_color = typedArray.getColor(index, Color.WHITE);
                    break;
                case R.styleable.XiaoMiStep_lineColor:  //最外侧线的颜色
                    line_color = typedArray.getColor(index, Color.WHITE);
                    break;
                case R.styleable.XiaoMiStep_ringColor: //圆环的颜色
                    ring_color = typedArray.getColor(index, Color.WHITE);
                    break;
                case R.styleable.XiaoMiStep_stepNumColor: //步数的颜色
                    step_num_color = typedArray.getColor(index, Color.WHITE);
                    break;
                case R.styleable.XiaoMiStep_otherTextColor: //其他文字颜色
                    other_text_color = typedArray.getColor(index, Color.WHITE);
                    break;
                default:
                    break;
            }
        }

        typedArray.recycle();
        init();
    }

    public int getMyFootNum() {
        return myFootNum;
    }

    public void setMyFootNum(int myFootNum) {
        this.myFootNum = myFootNum;
    }


    public void reSet(int footNum) {
        this.myFootNum = footNum;
        startAnim();
    }

    public void reSet() {
        startAnim();
    }



    private void init() {
        // 初始化画笔等
        mPaint = new Paint();
        mPaint.setAntiAlias(true); // 设置抗锯齿

        arcPaint = new Paint();
        arcPaint.setAntiAlias(true);

        textPaint = new Paint();  // 文字画笔
        textPaint.setAntiAlias(true);

        pointPaint = new Paint(); // 点
        pointPaint.setAntiAlias(true);

        animatorSet = new AnimatorSet();
    }

    /**
     * 重写onMeasure()方法，支持wrap_content属性
     *
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width;
        int height;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);  //宽度的测量模式
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);  //宽度的测量值
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);  //高度的测量模式
        int heightSize = MeasureSpec.getSize(heightMeasureSpec); //高度的测量值
        //如果布局里面设置的是固定值,这里取布局里面的固定值;如果设置的是match_parent,则取父布局的大小
        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else {
            //如果布局里面没有设置固定值,这里取布局的宽度的1/2
            width = widthSize / 2;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            //如果布局里面没有设置固定值,这里取布局的高度的3/4
            height = heightSize * 3 / 4;
        }

        widthBg = width;
        heightBg = height;
        Logger.d("背景坐标: " + widthBg + " , " + heightBg);  // 背景坐标: 720 , 974
        radius_out_circle = heightBg * 3 / 9;
        radius_inner_circle = heightBg * 3 / 10;
        line_length = 30;
        setMeasuredDimension(width, height);
        startAnim();
    }

    private void startAnim() {
        //小圆点动画
        final ValueAnimator dotAnimator = ValueAnimator.ofInt(-90, (myFootNum * 360 / 8000 - 90));

        dotAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                angle = (int) dotAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        dotAnimator.setInterpolator(new LinearInterpolator());


        //步数动画实现
        final ValueAnimator walkAnimator = ValueAnimator.ofInt(0, myFootNum);
        walkAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentFootNum = (int) walkAnimator.getAnimatedValue();
                postInvalidate();
            }
        });


        //画弧动画的实现
        final ValueAnimator arcAnimator = ValueAnimator.ofInt(0, (myFootNum * 360 / 8000));
        arcAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentFootNumPre = (int) arcAnimator.getAnimatedValue();
                postInvalidate();
            }
        });
        animatorSet.setDuration(2000);
        animatorSet.playTogether(walkAnimator, arcAnimator, dotAnimator);
        animatorSet.start();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //绘制底层背景（绘制矩形区域）
        mPaint.setColor(background_color);
        mPaint.setStyle(Paint.Style.FILL);
        RectF rectF_back = new RectF(0, 0, widthBg, heightBg);
        canvas.drawRect(rectF_back, mPaint);
        //绘制最外层的圆
        mPaint.setColor(out_circle_color);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        // 圆点；中心点， 半径是高度的1/3
        canvas.drawCircle(widthBg / 2, heightBg / 2, radius_out_circle, mPaint);
        //绘制圆上的小圆点
        pointPaint.setColor(out_dot_color);
        pointPaint.setStrokeWidth(10);
        // TODO 关于这里的画圆需要花时间分析
        canvas.drawCircle((float) (widthBg / 2 + radius_out_circle * Math.cos(angle * 3.14 / 180)),
                (float) (heightBg / 2 + radius_out_circle * Math.sin(angle * 3.14 / 180)),
                10,
                pointPaint);

        //画line
        drawLines(canvas);

        //画圆弧
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setStrokeWidth(30);
        arcPaint.setColor(ring_color);
        RectF arcRect = new RectF(
                (widthBg / 2 - radius_inner_circle + line_length / 2),
                (heightBg / 2 - radius_inner_circle + line_length / 2),
                (widthBg / 2 + radius_inner_circle - line_length / 2),
                (heightBg / 2 + radius_inner_circle - line_length / 2));
        canvas.drawArc(arcRect, -90, currentFootNumPre, false, arcPaint);

        //绘制步数
        textPaint.setColor(step_num_color);
        textPaint.setStrokeWidth(25);
        textPaint.setTextSize(widthBg / 6);
        canvas.drawText(String.valueOf(currentFootNum), (widthBg / 3 - 50), heightBg / 2 + 50, textPaint);
        textPaint.setStrokeWidth(10);
        textPaint.setColor(other_text_color);
        textPaint.setTextSize(widthBg / 20);
        canvas.drawText("步", (widthBg / 2 + 200), heightBg / 2 + 50, textPaint);

        //绘制公里
        currentDistance = (float) (myFootNum * 6.4 / 8000);
        //小数点后一位
        DecimalFormat df = new DecimalFormat("#.0");
        String currentDis = df.format(currentDistance);
        canvas.drawText(currentDis + "公里", (widthBg / 3 - 30), heightBg / 2 + 150, textPaint);
        //中间竖线
        mPaint.setStrokeWidth(8);
        canvas.drawLine(widthBg / 2 + 10, heightBg / 2 + 110, widthBg / 2 + 10, heightBg / 2 + 155, mPaint);
        //绘制卡路里
        currentCal = myFootNum * 230 / 8000;
        canvas.drawText(String.valueOf(currentCal) + "千卡", (widthBg / 2 + 40), heightBg / 2 + 150, textPaint);

    }

    /**
     * 绘制内层线条
     *
     * @param canvas
     */
    private void drawLines(Canvas canvas) {
        mPaint.setColor(line_color);
        mPaint.setStrokeWidth(4);
        for (int i = 0; i < 360; i++) {
            canvas.drawLine(widthBg / 2,
                    heightBg / 2 - radius_inner_circle,
                    widthBg / 2,
                    heightBg / 2 - radius_inner_circle + line_length,
                    mPaint);
            canvas.rotate(1, widthBg / 2, heightBg / 2);
        }
    }
}
