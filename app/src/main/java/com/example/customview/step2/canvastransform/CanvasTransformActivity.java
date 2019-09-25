package com.example.customview.step2.canvastransform;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.customview.R;
import com.example.customview.step2.MyView;
import com.example.customview.step2.view.CanvasActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by jinhui on 2018/1/24.
 * Email:1004260403@qq.com
 * <p>
 * 自定义控件之绘图篇（四）：canvas变换与操作
 */

public class CanvasTransformActivity extends AppCompatActivity {

    @BindView(R.id.bt_translate)
    Button btTranslate;
    @BindView(R.id.bt_screen)
    Button btScreen;
    @BindView(R.id.bt_Rotate)
    Button btRotate;
    @BindView(R.id.bt_scale)
    Button btScale;
    @BindView(R.id.bt_skew)
    Button btSkew;
    @BindView(R.id.bt_clip)
    Button btClip;
    @BindView(R.id.bt_save_restore)
    Button btSaveRestore;
    @BindView(R.id.bt_more_save)
    Button btMoreSave;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvastransform);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.bt_translate, R.id.bt_screen, R.id.bt_Rotate, R.id.bt_scale,
            R.id.bt_skew, R.id.bt_clip, R.id.bt_save_restore, R.id.bt_more_save})
    public void onViewClicked(View view) {
        MyView.DrawMode drawMode = null;
        switch (view.getId()) {
            case R.id.bt_translate:
                drawMode = MyView.DrawMode.Translate;
                break;
            case R.id.bt_screen:
                drawMode = MyView.DrawMode.Screen;
                break;
            case R.id.bt_Rotate:
                drawMode = MyView.DrawMode.Rotate;
                break;
            case R.id.bt_scale:
                drawMode = MyView.DrawMode.Scale;
                break;
            case R.id.bt_skew:
                drawMode = MyView.DrawMode.Skew;
                break;
            case R.id.bt_clip:
                drawMode = MyView.DrawMode.Clip;
                break;
            case R.id.bt_save_restore:
                drawMode = MyView.DrawMode.Save_restore;
                break;
            case R.id.bt_more_save:
                drawMode = MyView.DrawMode.More_save;
                break;
        }

        Intent intent = new Intent(this, CanvasActivity.class);
        intent.putExtra("drawMode", drawMode.getValue());
        startActivity(intent);
    }


}
