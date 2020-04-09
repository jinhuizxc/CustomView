package com.zx.customview.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.zx.customview.R;
import com.zx.customview.widget.loadingview.CircleView;

public class CircleViewActivity extends AppCompatActivity {

    private CircleView circleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_view);
    }
}
