package com.example.customview.step2.path.waveview;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.customview.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by jinhui on 2018/1/26.
 * Email:1004260403@qq.com
 */

public class WaveViewActivity extends AppCompatActivity {

    @BindView(R.id.waveview)
    WaveView waveview;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_waveview);
        ButterKnife.bind(this);

        waveview.startAnim();
    }
}
