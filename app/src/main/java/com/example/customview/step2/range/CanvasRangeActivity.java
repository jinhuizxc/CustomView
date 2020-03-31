package com.example.customview.step2.range;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.customview.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jinhui on 2018/1/24.
 * Email:1004260403@qq.com
 */

public class CanvasRangeActivity extends AppCompatActivity {

    @BindView(R.id.regionView)
    RegionView regionView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvasrange);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        if(intent != null){
            int value = intent.getIntExtra("drawMode", 0);
            regionView.setDrawMode(RegionView.DrawMode.valueOf(value));
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (regionView != null){
            regionView = null;
        }
    }
}
