package com.example.customview.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.customview.R;

/**
 * 支持对任意 View 添加未读消息红点
 * https://github.com/hongtaoStudio/DotView
 */
public class DotViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dot_view);
    }
}
