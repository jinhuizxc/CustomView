package com.zx.customview.step2.canvasbase.line;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import androidx.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by jinhui on 2018/1/23.
 * Email:1004260403@qq.com
 */

public class LineView extends View {

    public LineView(Context context) {
        super(context);
    }

    public LineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        doLine(canvas);
    }


    /**
     * 1、画直线
     * void drawLine (float startX, float startY, float stopX, float stopY, Paint paint)
     * 参数：
     * startX:开始点X坐标
     * startY:开始点Y坐标
     * stopX:结束点X坐标
     * stopY:结束点Y坐标
     */
    private void doLine(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(Color.RED);  // 设置画笔颜色
        paint.setStyle(Paint.Style.FILL);  // 设置填充样式
        paint.setStrokeWidth(5);  //设置画笔宽度
        canvas.drawLine(100, 100, 200, 200, paint);


        paint.setAntiAlias(true);//抗锯齿功能
        paint.setColor(Color.RED);  //设置画笔颜色
        paint.setStyle(Paint.Style.FILL);//设置填充样式   Style.FILL/Style.FILL_AND_STROKE/Style.STROKE
        paint.setStrokeWidth(5);//设置画笔宽度
        paint.setShadowLayer(10, 15, 15, Color.GREEN);//设置阴影
//        //设置画布背景颜色, 加上的话只能绘制本条线
//        canvas.drawRGB(255, 255, 255);
        canvas.drawLine(300, 300, 400, 400, paint);
    }

}
