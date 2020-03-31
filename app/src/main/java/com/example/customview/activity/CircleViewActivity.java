package com.example.customview.activity;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.example.customview.R;
import com.example.customview.widget.loadingview.CircleView;

public class CircleViewActivity extends AppCompatActivity {

    private CircleView circleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_circle_view);
    }
}
