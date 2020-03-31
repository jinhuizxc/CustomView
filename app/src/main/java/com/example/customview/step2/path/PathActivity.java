package com.example.customview.step2.path;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.customview.R;
import com.example.customview.step2.MyView;
import com.example.customview.step2.path.gestures.GesturesActivity;
import com.example.customview.step2.path.waveview.WaveViewActivity;
import com.example.customview.step2.view.CanvasActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jinhui on 2018/1/25.
 * Email:1004260403@qq.com
 * <p>
 * 自定义控件三部曲之绘图篇（六）——Path之贝赛尔曲线和手势轨迹、水波纹效果
 * <p>
 * 从这篇开始，我将延续androidGraphics系列文章把图片相关的知识给大家讲完，这一篇先稍微进阶一下，给大家把《android Graphics（二）：路径及文字》略去的quadTo（二阶贝塞尔）函数，给大家补充一下。
 * 本篇最终将以两个例子给大家演示贝塞尔曲线的强大用途：
 * 1.利用贝塞尔曲线，我们能实现平滑的手势轨迹效果
 * 2.电池充电时，有些手机会显示水波纹效果，就是这样做出来的。
 * <p>
 * //二阶贝赛尔
 * public void quadTo(float x1, float y1, float x2, float y2)
 * public void rQuadTo(float dx1, float dy1, float dx2, float dy2)
 * //三阶贝赛尔
 * public void cubicTo(float x1, float y1, float x2, float y2,float x3, float y3)
 * public void rCubicTo(float x1, float y1, float x2, float y2,float x3, float y3)
 */

public class PathActivity extends AppCompatActivity {

    @BindView(R.id.bt_gestures1)
    Button btGestures1;
    @BindView(R.id.bt_waveView)
    Button btWaveView;
    @BindView(R.id.bt_sin)
    Button btSin;
    @BindView(R.id.bt_rQuadTo)
    Button btRQuadTo;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_path);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_sin, R.id.bt_gestures1, R.id.bt_waveView, R.id.bt_rQuadTo})
    public void onViewClicked(View view) {
        MyView.DrawMode drawMode = null;
        switch (view.getId()) {
            case R.id.bt_gestures1:
//                drawMode = MyView.DrawMode.Gestures1;
                break;
            case R.id.bt_waveView:
//                drawMode = MyView.DrawMode.WaveView;
                break;
            case R.id.bt_sin:
                drawMode = MyView.DrawMode.Sin;
                break;
            case R.id.bt_rQuadTo:
                drawMode = MyView.DrawMode.rQuadTo;
                break;
        }
        // 手势轨迹1、2 单独给自定义view做
        if (view.getId() == R.id.bt_gestures1) {
            Intent intent = new Intent(this, GesturesActivity.class);
            startActivity(intent);
        } else if (view.getId() == R.id.bt_waveView) {
            Intent intent = new Intent(this, WaveViewActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, CanvasActivity.class);
            intent.putExtra("drawMode", drawMode.getValue());
            startActivity(intent);
        }

    }

}
