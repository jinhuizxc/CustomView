package com.zx.workclockview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;

import java.util.Calendar;

/**
 * 文字时钟：
 * 分析下涉及到的元素及样式表现
 * 「圆中信息」圆中心的数字时间 + 数字日期 + 文字星期几，始终为白色
 * 「时圈」一圈文字小时，一点、二点.. 十二点，当前点数为白色，其它为白色 + 透明度，如图中十点就是白色。
 * 「分圈」一圈文字分钟，一分、二分.. 五十九分，六十分显示为空，同理，当前分钟为白色，其它白色 + 透明度。
 * 「秒圈」一圈文字秒，一秒、二秒.. 五十九秒，六十秒显示为空，也是同理。
 * <p>
 * 然后分析下动画效果：
 * 每秒钟「秒圈」走一下，这一下的旋转角度为 360°/60=6°，并且走这一下的时候有个线性旋转过去的动画效果。
 * 每分钟「分圈」走一下，旋转角度和动画效果跟「秒圈」相同。
 * 每小时「时圈」走一下，旋转角度为 360°/12=30°，动画效果同上。
 */
public class WorkClockView extends View {

    /**
     * 全局画笔
     */
    private Paint mPaint = createPaint();
    //    private Paint mHelperPaint;
    // view的宽度、高度
    private float mWidth = -1f;
    private float mHeight;
    // 时间
    private float mHour;
    private float mMinute;
    private float mSecond;
    // 角度
    private float mHourDeg;
    private float mMinuteDeg;
    private float mSecondDeg;

    private ValueAnimator mAnimator;

    private String[] NUMBER_TEXT_LIST = {
            "日",
            "一",
            "二",
            "三",
            "四",
            "五",
            "六",
            "七",
            "八",
            "九",
            "十"};


    public WorkClockView(Context context) {
        this(context, null);
    }


    public WorkClockView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public WorkClockView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

        initAnimation();
    }

    /**
     * 在onLayout方法中计算View去除padding后的宽高
     *
     * @param changed
     * @param left
     * @param top
     * @param right
     * @param bottom
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = (float) (getMeasuredWidth() - getPaddingLeft() - getPaddingRight());
        mHeight = (float) (getMeasuredHeight() - getPaddingTop() - getPaddingBottom());

        mHour = mWidth * 0.143f;
        mMinute = mWidth * 0.35f;
        mSecond = mWidth * 0.35f;
    }

    //在onDraw方法将画布原点平移到中心位置
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (canvas == null) return;
        canvas.drawColor(Color.BLACK);  //填充背景
        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2); //原点移动到中心

        //绘制各元件
        drawCenterInfo(canvas);
        drawHour(canvas, mHourDeg);
        drawMinute(canvas, mMinuteDeg);
        drawSecond(canvas, mSecondDeg);

        //辅助线
        //canvas.drawLine(0f, 0f, mWidth, 0f, mHelperPaint)
        canvas.restore();
    }

    /**
     * 绘制秒
     */
    private void drawSecond(Canvas canvas, float degrees) {
        mPaint.setTextSize(mHour * 0.16f);
        //处理整体旋转
        canvas.save();
        canvas.rotate(degrees);
        for (int i = 0; i < 60; i++) {
            canvas.save();
            float iDeg = 360 / 60f * i;
            canvas.rotate(iDeg);
            if (iDeg + degrees == 0f)
                mPaint.setAlpha(255);
            else mPaint.setAlpha((int) (0.6f * 255));
            mPaint.setTextAlign(Paint.Align.LEFT);

            if (i < 59) {
                canvas.drawText(toText(i + 1) + "秒", mSecond, getCenteredY(), mPaint);
            }
            canvas.restore();
        }

        canvas.restore();
    }

    /**
     * 绘制分钟
     */
    private void drawMinute(Canvas canvas, float degrees) {
        mPaint.setTextSize(mHour * 0.16f);

        //处理整体旋转
        canvas.save();
        canvas.rotate(degrees);

        for (int i = 0; i < 60; i++) {
            canvas.save();
            float iDeg = 360 / 60f * i;
            canvas.rotate(iDeg);
            if (iDeg + degrees == 0f)
                mPaint.setAlpha(255);
            else
                mPaint.setAlpha((int) (0.6f * 255));
            mPaint.setTextAlign(Paint.Align.RIGHT);
            if (i < 59) {
                canvas.drawText(toText(i + 1) + "分", mMinute, getCenteredY(), mPaint);
            }
            canvas.restore();
        }

        canvas.restore();
    }

    /**
     * 绘制小时，度数
     *
     * @param canvas
     * @param degree
     */
    private void drawHour(Canvas canvas, float degree) {
        mPaint.setTextSize(mHour * 0.16f);

        //处理整体旋转
        canvas.save();
        canvas.rotate(degree);
        for (int i = 0; i < 12; i++) {
            canvas.save();

            //从x轴开始旋转，每30°绘制一下「几点」，12次就画完了「时圈」
            float iDeg = 360 / 12f * i;
            canvas.rotate(iDeg);

            if (iDeg + degree == 0f) {
                mPaint.setAlpha(255);
            } else {
                mPaint.setAlpha((int) (0.6f * 255));
            }
            mPaint.setTextAlign(Paint.Align.LEFT);

            canvas.drawText(toText(i + 1) + "点", mHour, getCenteredY(), mPaint);
            canvas.restore();
        }
        canvas.restore();
    }


    /**
     * 绘制圆中信息
     */
    private void drawCenterInfo(Canvas canvas) {
        int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
        int minute = Calendar.getInstance().get(Calendar.MINUTE);
        String minuteStr;
        if (minute < 10) {
            minuteStr = "0" + minute;
        } else {
            minuteStr = "" + minute;
        }

        mPaint.setTextSize(mHour * 0.4f);
        mPaint.setAlpha(255);
        mPaint.setTextAlign(Paint.Align.CENTER);

        canvas.drawText(hour + ":" + minuteStr, 0f, getBottomedY(), mPaint);

        //绘制月份、星期
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        String monthStr;
        if (month < 10) {
            monthStr = "0" + month;
        } else {
            monthStr = "" + month;
        }
//        val month = (this.get(Calendar.MONTH) + 1).let {
//            if (it < 10) "0$it" else "$it"
//        }
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        String dayOfWeek = toText((Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1));

        mPaint.setTextSize(mHour * 0.16f);
        mPaint.setAlpha(255);
        mPaint.setTextAlign(Paint.Align.CENTER);
        canvas.drawText(monthStr + "." + day + " 星期" + dayOfWeek, 0f, getToppedY(), mPaint);
    }


    /**
     * 初始化宽高，供动态壁纸使用
     */
    public void initWidthHeight(float width, float height) {
        if (mWidth < 0) {
            mWidth = width;
            mHeight = height;

            mHour = mWidth * 0.143f;
            mMinute = mWidth * 0.35f;
            mSecond = mWidth * 0.35f;
        }
    }

    /**
     * 处理动画，声明全局的处理器
     */
    private void initAnimation() {
        mAnimator = ValueAnimator.ofFloat(6f, 0f); //由6降到1
        mAnimator.setDuration(150);
        mAnimator.setInterpolator(new LinearInterpolator()); //插值器设为线性
        doInvalidate();
    }

    /**
     * 开始绘制
     */
    public void doInvalidate() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        final int minute = calendar.get(Calendar.MINUTE);
        final int second = calendar.get(Calendar.SECOND);

        mHourDeg = -360 / 12f * (hour - 1);
        mMinuteDeg = -360 / 60f * (minute - 1);
        mSecondDeg = -360 / 60f * (second - 1);

        //记录当前角度，然后让秒圈线性的旋转6°
        final float hd = mHourDeg;
        final float md = mMinuteDeg;
        final float sd = mSecondDeg;
        //处理动画
        mAnimator.removeAllUpdateListeners(); //需要移除先前的监听
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float av = (float) animation.getAnimatedValue();

                if (minute == 0 && second == 0) {
                    mHourDeg = hd + av * 5;  //时圈旋转角度是分秒的5倍，线性的旋转30°
                }

                if (second == 0) {
                    mMinuteDeg = md + av;  //线性的旋转6°
                }

                mSecondDeg = sd + av; //线性的旋转6°

                invalidate();  // 刷新
            }
        });

        mAnimator.start();
    }

    private void init() {

    }

    /**
     * 数字转换文字
     */
    private static final String TAG = "WorkClockView";

    public String toText(int num) {
//        String[] nums = {"零","一","二","三","四","五","六","七","八","九"};
        String result = "";
        String str = String.valueOf(num);
        char[] charNum = str.toCharArray();
        int length = str.length();
        for (int i = 0; i < length; i++) {
            int c = charNum[i] - '0';
            Log.d(TAG, "toText: c =====> " + c);  // 0 -9
            if (c != 0) {
                result += NUMBER_TEXT_LIST[c];
            } else {
                result += NUMBER_TEXT_LIST[c];
            }
        }
        return result;
    }

    // 比如输入“123”，返回“一百二十三”.
    /**
     * java面试题：输入一个数字，将其转化为汉字形式
     * https://blog.csdn.net/qq_35387891/article/details/80720401?depth_1-utm_source=distribute.pc_relevant.none-task&utm_source=distribute.pc_relevant.none-task
     *
     * @param num
     * @return
     */
    /*public static void toText(int num) {
        String[] nums = {"零","一","二","三","四","五","六","七","八","九"};
        String[] unit = {"","十","百","千","万","十","百","千","亿","十","百","千","万亿"};
        String str = String.valueOf(num);
        char[] charNum = str.toCharArray();
        String result = "";
        int length = str.length();
        for(int i = 0; i < length; i++) {
            int c = charNum[i] - '0';
            if(c != 0) {
                result += nums[c] + unit[length - i - 1];
            }else{
                result +=  nums[c];
            }
        }
        System.out.println(result);;
    }*/


    /**
     * 创建画笔
     */
    private Paint createPaint() {
        mPaint = new Paint();
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        return mPaint;
    }


    /**
     * 扩展获取绘制文字时在x轴上 垂直居中的y坐标
     */
    private float getCenteredY() {
        return mPaint.getFontSpacing() / 2 - mPaint.getFontMetrics().bottom;
    }

    /**
     * 扩展获取绘制文字时在x轴上 贴紧x轴的上边缘的y坐标
     */
    private float getBottomedY() {
        return -mPaint.getFontMetrics().bottom;
    }

    /**
     * 扩展获取绘制文字时在x轴上 贴近x轴的下边缘的y坐标
     */
    private float getToppedY() {
        return -mPaint.getFontMetrics().ascent;
    }

    /**
     * 停止后续绘制，供动态壁纸使用
     */
    public void stopInvalidate() {
        mAnimator.removeAllUpdateListeners();
    }

    private float dp2px(float dpValue) {
        float scale = getResources().getDisplayMetrics().density;
        return (dpValue * scale) + 0.5f;
    }
}
