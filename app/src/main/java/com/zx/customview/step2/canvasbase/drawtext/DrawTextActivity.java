package com.zx.customview.step2.canvasbase.drawtext;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.zx.customview.R;
import com.zx.customview.step2.MyView;
import com.zx.customview.step2.view.CanvasActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jinhui on 2018/1/25.
 * Email:1004260403@qq.com
 * <p>
 * 自定义控件之绘图篇（ 五）：drawText()详解
 */

public class DrawTextActivity extends AppCompatActivity {

    @BindView(R.id.bt_drawText)
    Button btDrawText;
    @BindView(R.id.bt_setTextAlign)
    Button btSetTextAlign;
    @BindView(R.id.bt_adtb)
    Button btAdtb;
    @BindView(R.id.bt_what)
    Button btWhat;
    @BindView(R.id.bt_write_left)
    Button btWriteLeft;
    @BindView(R.id.bt_write_center)
    Button btWriteCenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drawtext);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_drawText, R.id.bt_setTextAlign, R.id.bt_adtb,
            R.id.bt_what, R.id.bt_write_left, R.id.bt_write_center})
    public void onViewClicked(View view) {
        MyView.DrawMode drawMode = null;
        switch (view.getId()) {
            case R.id.bt_drawText:
                drawMode = MyView.DrawMode.DrawText;
                break;
            case R.id.bt_setTextAlign:
                drawMode = MyView.DrawMode.SetTextAlign;
                break;
            case R.id.bt_adtb:
                drawMode = MyView.DrawMode.Adtb;
                break;
            case R.id.bt_what:
                drawMode = MyView.DrawMode.What;
                break;
            case R.id.bt_write_left:
                drawMode = MyView.DrawMode.Write_left;
                break;
            case R.id.bt_write_center:
                drawMode = MyView.DrawMode.Write_center;
                break;
        }

        Intent intent = new Intent(this, CanvasActivity.class);
        intent.putExtra("drawMode", drawMode.getValue());
        startActivity(intent);
    }


}
