package com.example.customview.step2.canvasbase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.customview.R;
import com.example.customview.step2.MyView;
import com.example.customview.step2.canvasbase.line.LineActivity;
import com.example.customview.step2.view.CanvasActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jinhui on 2018/1/23.
 * Email:1004260403@qq.com
 * <p>
 * Paint与Canvas
 * 像我们平时画图一样，需要两个工具，纸和笔。Paint就是相当于笔，而Canvas就是纸，这里叫画布。
 * <p>
 * 所以，凡有跟要要画的东西的设置相关的，比如大小，粗细，画笔颜色，透明度，字体的样式等等，都是在Paint里设置；同样，凡是要画出成品的东西，比如圆形，矩形，文字等相关的都是在Canvas里生成。
 * 下面先说下Paint的基本设置函数：
 * paint.setAntiAlias(true);//抗锯齿功能
 * paint.setColor(Color.RED);  //设置画笔颜色
 * paint.setStyle(Style.FILL);//设置填充样式
 * paint.setStrokeWidth(30);//设置画笔宽度
 * paint.setShadowLayer(10, 15, 15, Color.GREEN);//设置阴影
 * <p>
 * 前两个没什么好说的，看填充样式的区别：
 * <p>
 * 1、void setStyle (Paint.Style style)     设置填充样式
 * <p>
 * Paint.Style.FILL    :填充内部
 * Paint.Style.FILL_AND_STROKE  ：填充内部和描边
 * Paint.Style.STROKE  ：仅描边
 * <p>
 * 看下这三个类型的不同，下面以画的一个圆形为例：
 * 可见，FILL与FILL_AND_STROKE没什么区别。
 * <p>
 * 2、setShadowLayer (float radius, float dx, float dy, int color)    添加阴影
 * 参数：
 * radius:阴影的倾斜度
 * dx:水平位移
 * dy:垂直位移
 * <p>
 * 使用代码：
 * paint.setShadowLayer(10, 15, 15, Color.GREEN);//设置阴影
 * <p>
 * 然后是Canvas的基本设置：
 * 画布背景设置：
 * canvas.drawColor(Color.BLUE);
 * canvas.drawRGB(255, 255, 0);   //这两个功能一样，都是用来设置背景颜色的。
 */

public class CanvasBaseActivity extends AppCompatActivity {

    @BindView(R.id.bt_Line)
    Button btLine;
    @BindView(R.id.bt_Lines)
    Button btLines;
    @BindView(R.id.bt_Point)
    Button btPoint;
    @BindView(R.id.bt_Points)
    Button btPoints;
    @BindView(R.id.bt_Rect)
    Button btRect;
    @BindView(R.id.bt_RoundRect)
    Button btRoundRect;
    @BindView(R.id.bt_Circle)
    Button btCircle;
    @BindView(R.id.bt_Oval)
    Button btOval;
    @BindView(R.id.bt_Arc)
    Button btArc;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvasbase);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_Line, R.id.bt_Lines, R.id.bt_Point, R.id.bt_Points,
            R.id.bt_Rect, R.id.bt_RoundRect, R.id.bt_Circle, R.id.bt_Oval,
            R.id.bt_Arc})
    public void onViewClicked(View view) {
        MyView.DrawMode drawMode = null;
        switch (view.getId()) {
            case R.id.bt_Line:
                // 这个界面是基本配置，之后界面采用枚举进行配置，执行会闪退，下面执行。
//                startActivity(new Intent(this, LineActivity.class));
                break;
            case R.id.bt_Lines:
                drawMode = MyView.DrawMode.Lines;
                break;
            case R.id.bt_Point:
                drawMode = MyView.DrawMode.Point;
                break;
            case R.id.bt_Points:
                drawMode = MyView.DrawMode.Points;
                break;
            case R.id.bt_Rect:
                drawMode = MyView.DrawMode.Rect;
                break;
            case R.id.bt_RoundRect:
                drawMode = MyView.DrawMode.RoundRect;
                break;
            case R.id.bt_Circle:
                drawMode = MyView.DrawMode.Circle;
                break;
            case R.id.bt_Oval:
                drawMode = MyView.DrawMode.Oval;
                break;
            case R.id.bt_Arc:
                drawMode = MyView.DrawMode.Arc;
                break;

        }
        if (view.getId() == R.id.bt_Line) {
            // 这个界面是基本配置，之后界面采用枚举进行配置
            startActivity(new Intent(this, LineActivity.class));
        } else {
            Intent intent = new Intent(this, CanvasActivity.class);
            intent.putExtra("drawMode", drawMode.getValue());
            startActivity(intent);
        }

    }

}
