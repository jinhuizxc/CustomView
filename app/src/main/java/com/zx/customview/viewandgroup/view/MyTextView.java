package com.zx.customview.viewandgroup.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

public class MyTextView extends AppCompatTextView {

    private static final String TAG = "MyTextView";

    private Paint mPaint1, mPaint2;


    public MyTextView(Context context) {
        super(context);
        init();
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    /**
     * 初始化
     */
    private void init() {
        mPaint1 = new Paint();
        mPaint1.setColor(Color.RED);
        mPaint1.setStyle(Paint.Style.FILL);

        mPaint2 = new Paint();
        mPaint2.setColor(Color.YELLOW);
        mPaint2.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
        //在实现原生控件之间，实现我们的逻辑
        //绘制外层矩形
      int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int width = getWidth();
        int height = getHeight();
        /**
         *
         * 获取宽高值: 400 , 200 , 400 , 200
         * 测试发现数值是相同的
         */
        Log.d(TAG, "获取宽高值: " + measuredWidth + " , "
                + measuredHeight + " , "
                + width + " , "
                + height);

        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint1);
        //绘制内层矩形
        canvas.drawRect(10, 10, getMeasuredWidth() - 10, getMeasuredHeight() - 10, mPaint2);

        // 特别需要注意对于view的绘制有个重要的点，那就是"绘制覆盖"！
        // 如果在绘制矩形之前调用 super.onDraw(canvas); 绘制文本上的字，在之后则不会。
//        super.onDraw(canvas);

        //保存画布状态
        canvas.save();
        // 绘制文字前各平移100像素
        canvas.translate(100, 100);
        // 父类完成的方法，绘制文字
        super.onDraw(canvas);   // 进行绘制
        canvas.restore();   // 进行状态恢复


    }
}
