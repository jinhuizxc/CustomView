package com.zx.customview.step2;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.zx.customview.R;
import com.zx.customview.step2.canvasbase.CanvasBaseActivity;
import com.zx.customview.step2.canvasbase.drawtext.DrawTextActivity;
import com.zx.customview.step2.canvastransform.CanvasTransformActivity;
import com.zx.customview.step2.path.PathActivity;
import com.zx.customview.step2.pathtext.PathTextActivity;
import com.zx.customview.step2.range.RangeActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jinhui on 2018/1/23.
 * Email:1004260403@qq.com
 * <p>
 * 自定义控件三部曲之绘图篇
 */

public class Step2Activity extends AppCompatActivity {

    @BindView(R.id.bt_base)
    Button btBase;
    @BindView(R.id.bt_path_text)
    Button btPathText;
    @BindView(R.id.bt_range)
    Button btRange;
    @BindView(R.id.bt_canvas)
    Button btCanvas;
    @BindView(R.id.bt_drawText)
    Button btDrawText;
    @BindView(R.id.bt_Path)
    Button btPath;
    @BindView(R.id.bt_Paths)
    Button btPaths;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step2);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_base, R.id.bt_path_text, R.id.bt_range, R.id.bt_canvas, R.id.bt_drawText, R.id.bt_Path, R.id.bt_Paths})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_base:
                startActivity(new Intent(this, CanvasBaseActivity.class));
                break;
            case R.id.bt_path_text:
                startActivity(new Intent(this, PathTextActivity.class));
                break;
            case R.id.bt_range:
                startActivity(new Intent(this, RangeActivity.class));
                break;
            case R.id.bt_canvas:
                startActivity(new Intent(this, CanvasTransformActivity.class));
                break;
            case R.id.bt_drawText:
                startActivity(new Intent(this, DrawTextActivity.class));
                break;
            case R.id.bt_Path:
                startActivity(new Intent(this, PathActivity.class));
                break;
            case R.id.bt_Paths:
                break;
        }
    }
}
