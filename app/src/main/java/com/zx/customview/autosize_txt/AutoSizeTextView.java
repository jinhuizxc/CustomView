package com.zx.customview.autosize_txt;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.zx.customview.utils.PixelUtil;

/**
 * Android自定义控件：自适应大小的文本控件
 * https://www.jianshu.com/p/3e48dd0547a0
 * <p>
 * TextView 本身也是继承View的
 */
public class AutoSizeTextView extends View {

    private static final String TAG = "AutoSizeTextView";
    private Context context; // 上下文
    private int canvasWidth;  // 画布宽度
    private int canvasHeight;  // 画布高度
    private Paint paint;  // 画笔

    // 外界传入的默认字号（最大字号），单位SP，如果不传入，我们默认50sp，大家可自行修改
    private float maxTextSize;
    //外界传入的文本内容
    private String text = "";


    public AutoSizeTextView(Context context) {
        super(context);
        initPaint();
    }

    public AutoSizeTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initPaint();
    }

    public AutoSizeTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initPaint();
    }

    //初始化画笔属性
    private void initPaint() {
        //设置防锯齿
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
//        paint.setFlags(Paint.ANTI_ALIAS_FLAG);
        //设置画笔宽度
        paint.setStrokeWidth(1);
    }

    /**
     * 设置文本（供外界调用）
     *
     * @param text 文本
     */
    public AutoSizeTextView setText(String text) {
        this.text = text;
        return this;
    }

    /**
     * 设置支持的最大文本字号（供外界调用）
     * @param size 文本字号
     */
    public AutoSizeTextView setMaxTextSize(int size) {
        this.maxTextSize = size;
        return this;
    }

    /**
     * 当此视图的大小更改时，在布局过程中调用此方法。
     * 如果您刚刚被添加到视图层次结构中，则使用旧的
     *
     * 它的方法名已经告诉我们了，这个方法会在这个view的大小发生改变是被系统调用，
     * 我们要记住的就是view大小变化，
     * 这个方法就被执行就可以了，剩下的就是重写这个方法，
     * 在里面实现我们自己需求的业务逻辑
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        Log.d(TAG, "onSizeChanged 被调用");
        //每一次外观变化，都会调用该方法
        // 获得画布宽度,高度
        this.canvasWidth = getWidth();
        this.canvasHeight = getHeight();
        Log.d(TAG, "视图宽度, 高度: " + getWidth() + " , " + getHeight());
    }

    /**
     * 在View上绘制Canvas对象 一般会在onDraw函数
     * 但是onDraw是一个触发事件产生的调用 只能通过特定的方法 触发事件后来调用onDraw
     * 这样就需要调用invalidate或者postInvalidate方法
     * 可以在绘制完Canvas对象时 调用这2个方法就可以自动调用onDraw
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Log.d(TAG, "onDraw: 方法被调用");
        drawText(canvas);
    }

    //绘制文本
    private void drawText(Canvas canvas) {
        //根据画布宽度，获得合适的字号（即：刚好能显示满当前宽度的字号，与maxsize相比，只能小不能大）
        float textSize = getRightSize();
        //为画笔设置上合适的字号
        paint.setTextSize(PixelUtil.dp2px(getContext(), textSize));
        // 计算Baseline绘制的起点X轴坐标 ，计算方式：画布宽度的一半 - 文字宽度的一半
        int x = (int) ((canvasWidth - paint.measureText(text)) / 2);
        // 计算Baseline绘制的Y坐标 ，计算方式：画布高度的一半 - 文字总高度的一半
        int y = (int) ((canvasHeight / 2) - (paint.descent() + paint.ascent()) / 2);
        //绘制文本
        canvas.drawText(text, x, y, paint);
    }

    /**
     *  获得合适的字号
     * 绘制字号, 对齐方式
     *
     *  经过分析，大字体显示的宽度与小字体显示的宽度比值应是一致的，所以，公式如下：
     *     PreWidth: float preWidth = paint.measureText(text);
     *     MaxSize / PreWidth = x / canvasWidth;
     *     x = MaxSize * canvasWidth / PreWidth;
     *     x 就是我们需要重新设置的字号。
     *     得到字号，我们就可以为画笔设置文字大小了，也就可以通过画笔画出合适大小的文字了。
     *
     *     画笔字号大小/文本占用宽度 = 文本字号大小/画布宽度
     * @return
     */
    private float getRightSize() {
        paint.setTextSize(PixelUtil.dp2px(getContext(), maxTextSize));
        paint.setTextAlign(Paint.Align.LEFT);
        //根据最大值，计算出当前文本占用的宽度
        float preWidth = paint.measureText(text);
        //如果文本占用的宽度比画布宽度小，说明不用伸缩，直接返回当前字号
        if (preWidth < canvasWidth){
            return maxTextSize;
        }
        //已知当前文本字号、文本占用宽度、画布宽度，计算出合适的字号，并返回
        return maxTextSize * canvasWidth / preWidth;
    }



}
