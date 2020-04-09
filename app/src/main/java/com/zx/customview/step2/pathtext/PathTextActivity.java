package com.zx.customview.step2.pathtext;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.zx.customview.R;
import com.zx.customview.step2.MyView;
import com.zx.customview.step2.view.CanvasActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jinhui on 2018/1/24.
 * Email:1004260403@qq.com
 * <p>
 * 自定义控件之绘图篇（二）：路径及文字
 */

public class PathTextActivity extends AppCompatActivity {

    @BindView(R.id.bt_line_path)
    Button btLinePath;
    @BindView(R.id.bt_rect_path)
    Button btRectPath;
    @BindView(R.id.bt_roundrect_path)
    Button btRoundrectPath;
    @BindView(R.id.bt_circle_path)
    Button btCirclePath;
    @BindView(R.id.bt_oval_path)
    Button btOvalPath;
    @BindView(R.id.bt_arc_path)
    Button btArcPath;
    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.bt_quadTo)
    Button btQuadTo;
    @BindView(R.id.bt_paint_style)
    Button btPaintStyle;
    @BindView(R.id.bt_paint_style1)
    Button btPaintStyle1;
    @BindView(R.id.bt_paint_style2)
    Button btPaintStyle2;
    @BindView(R.id.bt_canvas)
    Button btCanvas;
    @BindView(R.id.bt_pos_text)
    Button btPosText;
    @BindView(R.id.bt_textOnPath)
    Button btTextOnPath;
    @BindView(R.id.bt_Typeface)
    Button btTypeface;
    @BindView(R.id.bt_customTypeface)
    Button btCustomTypeface;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pathtext);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_line_path, R.id.bt_rect_path, R.id.bt_roundrect_path,
            R.id.bt_circle_path, R.id.bt_oval_path, R.id.bt_arc_path,
            R.id.bt_quadTo, R.id.bt_paint_style, R.id.bt_paint_style1,
            R.id.bt_paint_style2, R.id.bt_canvas, R.id.bt_pos_text, R.id.bt_textOnPath, R.id.bt_Typeface, R.id.bt_customTypeface})
    public void onViewClicked(View view) {
        MyView.DrawMode drawMode = null;
        switch (view.getId()) {
            case R.id.bt_line_path:
                drawMode = MyView.DrawMode.Line_path;
                break;
            case R.id.bt_rect_path:
                drawMode = MyView.DrawMode.Rect_path;
                break;
            case R.id.bt_roundrect_path:
                drawMode = MyView.DrawMode.Roundrect_path;
                break;
            case R.id.bt_circle_path:
                drawMode = MyView.DrawMode.Circle_path;
                break;
            case R.id.bt_oval_path:
                drawMode = MyView.DrawMode.Oval_path;
                break;
            case R.id.bt_arc_path:
                drawMode = MyView.DrawMode.Arc_path;
                break;
            case R.id.bt_quadTo:
//                Toast.makeText(this, "参考Path之贝塞尔曲线", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_paint_style:
                drawMode = MyView.DrawMode.Paint_style;
                break;
            case R.id.bt_paint_style1:
                drawMode = MyView.DrawMode.Paint_style1;
                break;
            case R.id.bt_paint_style2:
                drawMode = MyView.DrawMode.Paint_style2;
                break;
            case R.id.bt_canvas:
//                drawMode = MyView.DrawMode.Canvas;
                Toast.makeText(this, "无例子,看下描述理解下即可", Toast.LENGTH_SHORT).show();
                break;
            case R.id.bt_pos_text:
                drawMode = MyView.DrawMode.Pos_text;
                break;
            case R.id.bt_textOnPath:
                drawMode = MyView.DrawMode.TextOnPath;
                break;
            case R.id.bt_Typeface:
                drawMode = MyView.DrawMode.Typeface;
                break;
            case R.id.bt_customTypeface:
                drawMode = MyView.DrawMode.CustomTypeface;
                break;
        }
        if (view.getId() == R.id.bt_quadTo) {
            Toast.makeText(this, "参考Path之贝塞尔曲线", Toast.LENGTH_SHORT).show();
        } else if (view.getId() == R.id.bt_canvas) {
            Toast.makeText(this, "无例子,看下描述理解下即可", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, CanvasActivity.class);
            intent.putExtra("drawMode", drawMode.getValue());
            startActivity(intent);
        }

    }

}
