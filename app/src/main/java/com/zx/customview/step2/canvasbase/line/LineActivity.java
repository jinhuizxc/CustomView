package com.zx.customview.step2.canvasbase.line;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.FrameLayout;

import com.zx.customview.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jinhui on 2018/1/23.
 * Email:1004260403@qq.com
 */

public class LineActivity extends AppCompatActivity {

    @BindView(R.id.fl)
    FrameLayout fl;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line);
        ButterKnife.bind(this);

        fl.addView(new LineView( this));
    }
}
