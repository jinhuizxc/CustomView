package com.example.customview.activity.flowlayout;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.blankj.utilcode.util.ActivityUtils;
import com.example.customview.activity.email.EmailTextAreaActivity;
import com.example.test.TestActivity;

public class SampleActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.flowlayout.R.layout.activity_sample);

        findViewById(com.example.flowlayout.R.id.btn_test).setOnClickListener(this);
        findViewById(com.example.flowlayout.R.id.btn_tv).setOnClickListener(this);
        findViewById(com.example.flowlayout.R.id.btn_tv_scroll).setOnClickListener(this);
        findViewById(com.example.flowlayout.R.id.btn_layout_scroll).setOnClickListener(this);
        findViewById(com.example.flowlayout.R.id.btn_layout__multi_scroll).setOnClickListener(this);
        findViewById(com.example.flowlayout.R.id.btn_select).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == com.example.flowlayout.R.id.btn_test) {
            ActivityUtils.startActivity(TestActivity.class);
        } else if (id == com.example.flowlayout.R.id.btn_tv) {
            ActivityUtils.startActivity(TvActivity.class);
        } else if (id == com.example.flowlayout.R.id.btn_tv_scroll) {
            ActivityUtils.startActivity(TvScrollActivity.class);
        } else if (id == com.example.flowlayout.R.id.btn_layout_scroll) {
            ActivityUtils.startActivity(LayoutScrollActivity.class);
        } else if (id == com.example.flowlayout.R.id.btn_layout__multi_scroll) {
            ActivityUtils.startActivity(LayoutMultiScrollActivity.class);
        }else if (id == com.example.flowlayout.R.id.btn_select){
            ActivityUtils.startActivity(EmailTextAreaActivity.class);
        }
    }
}
